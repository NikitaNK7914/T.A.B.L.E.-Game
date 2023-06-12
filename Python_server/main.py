import asyncio
import json

import websockets

SESSIONS = dict()


async def handler(websocket):
    global SESSIONS

    while True:
        try:
            message = await websocket.recv()
        except websockets.ConnectionClosedOK:
            break
        mess = json.loads(message)
        if mess["request"] == "POST":
            if mess["session"] in SESSIONS:
                for i in SESSIONS[mess["session"]]["users"]:
                    await SESSIONS[mess["session"]]["users"][i].send(message)
        elif mess["request"] == "DISCONNECT":
            del SESSIONS[mess["session"]]["users"][SESSIONS[mess["session"]]["users"].index(mess["id"])]
            SESSIONS[mess["session"]]["cnt"] -= 1
            if SESSIONS[mess["session"]]["cnt"] == 0:
                del SESSIONS[mess["session"]]
        elif mess["request"] == "CONNECT":
            if mess["session"] in SESSIONS:
                if not SESSIONS[mess["session"]]["start"]:
                    SESSIONS[mess["session"]]["users"][SESSIONS[mess["session"]]["cnt"]] = websocket
                    SESSIONS[mess["session"]]["cnt"] += 1
                    websocket.send(f"\"session\":{mess['session']},\"request\":\"CONNECT\", \"data\":{SESSIONS[mess['session']]['cnt']}")
                else:
                    websocket.send(f"\"session\":{mess['session']},\"request\":\"CONNECT\", \"data\":0")
            else:
                websocket.send(f"\"session\":{mess['session']},\"request\":\"CONNECT\", \"data\":0")
        elif mess["request"] == "ARMOR" or mess["request"] == "ATTACK":
            await SESSIONS[mess["session"]]["users"][mess["id_target"]].send(mess)
        elif mess["request"] == "START":
            SESSIONS[mess["session"]]["start"] = True
            for i in SESSIONS[mess["session"]]["users"]:
                await SESSIONS[mess["session"]]["users"][i].send("{\"request\":\"START\"")
        elif mess["request"] == "CREARE":
            if not(mess["session"] in SESSIONS:
                SESSIONS[mess["session"]] = {"cnt": 1, "start": False, "users": {0: websocket}}
                websocket.send(f"\"session\":{mess['session']},\"request\":\"CONNECT\", \"data\":1")
            else:
                websocket.send(f"\"session\":{mess['session']},\"request\":\"CONNECT\", \"data\":0")


async def main():
    async with websockets.serve(handler, "localhost", 8001):
        await asyncio.Future()  # run foreverif __name__ == "__main__":
    asyncio.run(main())