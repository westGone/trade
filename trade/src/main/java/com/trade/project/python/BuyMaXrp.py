# -*- coding: utf-8 -*-

import pandas as pd
import numpy as np
import plotly.graph_objects as go
import bybit
import time
import calendar
import sys
import time

from pybit import HTTP
from datetime import datetime

def buy_process() :
    user = [["vdxzqZLxzJtJaemOgX","Xs2atrYxi1llNO3mDHqTTMYKrO0nS5doPzBx"]]
    for i in range(len(user)) :
        # API 접근
        client = bybit.bybit(test=False, api_key=user[i][0], api_secret=user[i][1])
        # 내 포지션 정보
        pos         = client.Positions.Positions_myPosition(symbol="XRPUSD").result()
        # 내 주문 내역
        orderList   = client.Order.Order_getOrders(symbol="XRPUSD").result()
        # 현재 시장가격
        marketPrice = float(client.Market.Market_orderbook(symbol="XRPUSD").result()[0]['result'][0]['price'])
        # 현재 포지션 주문 수량
        buySize     = int(pos[0]['result']['size'])
        # 내 계좌 밸런스
        balance     = float(client.Wallet.Wallet_getBalance(coin="XRP").result()[0]['result']['XRP']['equity'])
        # 구매 수량
        size = round(marketPrice * balance * 50 * 0.65)
        
        if pos[0]['result']['side'] == "Buy" :
            print("AB")
    
        elif pos[0]['result']['side'] == "Sell" :
            # 지정가 예약 취소
            client.Order.Order_cancelAll(symbol="XRPUSD").result()
            time.sleep(1)
            # 매도 잔량 청산
            client.Order.Order_new(side="Buy",symbol="XRPUSD",order_type="Market",qty=int(buySize),time_in_force="GoodTillCancel").result()
            time.sleep(1)
            # 시장가 매수
            client.Order.Order_new(side="Buy",symbol="XRPUSD",order_type="Market",qty=int(size),time_in_force="GoodTillCancel").result()
            time.sleep(1)
            # 시장가 + 1000 지정가 예약
            client.Order.Order_new(side="Sell",symbol="XRPUSD",order_type="Limit",qty=int(size/2),price=marketPrice+0.005,time_in_force="GoodTillCancel").result()
            time.sleep(1)
            # 시장가 + 2000 지정가 예약
            client.Order.Order_new(side="Sell",symbol="XRPUSD",order_type="Limit",qty=int(size/2),price=marketPrice+0.01,time_in_force="GoodTillCancel").result()
            time.sleep(1)
            # 손절가 지정
            client.Positions.Positions_tradingStop(symbol="XRPUSD",stop_loss=str(marketPrice-0.007)).result()
            time.sleep(1)
            # 마진 변경
            client.Positions.Positions_changeMargin(symbol="XRPUSD", margin=str(round(balance*0.9))).result()
            time.sleep(1)
            print("LB",",",marketPrice, buySize)
        else :
            # 지정가 예약 취소
            client.Order.Order_cancelAll(symbol="XRPUSD").result()
            time.sleep(1)
            # 시장가 매수
            client.Order.Order_new(side="Buy",symbol="XRPUSD",order_type="Market",qty=int(size),time_in_force="GoodTillCancel").result()
            time.sleep(1)
            # 시장가 + 1000 지정가 예약
            client.Order.Order_new(side="Sell",symbol="XRPUSD",order_type="Limit",qty=int(size/2),price=marketPrice+0.005,time_in_force="GoodTillCancel").result()
            time.sleep(1)
            # 시장가 + 2000 지정가 예약
            client.Order.Order_new(side="Sell",symbol="XRPUSD",order_type="Limit",qty=int(size/2),price=marketPrice+0.01,time_in_force="GoodTillCancel").result()
            time.sleep(1)
            # 손절가 지정
            client.Positions.Positions_tradingStop(symbol="XRPUSD",stop_loss=str(marketPrice-0.007)).result()
            time.sleep(1)
            # 마진 변경
            client.Positions.Positions_changeMargin(symbol="XRPUSD", margin=str(round(balance*0.9))).result()
            time.sleep(1)
            print("BU",",",marketPrice)

    
buy_process()