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

def sell_process() :
    # API 접근
    client = bybit.bybit(test=False, api_key='vdxzqZLxzJtJaemOgX', api_secret='Xs2atrYxi1llNO3mDHqTTMYKrO0nS5doPzBx')
 
    # client.Order.Order_new(side="Buy",symbol="ETHUSD",order_type="Market",qty=1,time_in_force="GoodTillCancel").result()
    # Leverage 변경(적용안됨)
    # leverage = client.LinearPositions.LinearPositions_saveLeverage(symbol="ETHUSDT", buy_leverage=10, sell_leverage=10).result()
    
    # 내 포지션 정보
    pos         = client.Positions.Positions_myPosition(symbol="ETHUSD").result()
    # 내 주문 내역
    orderList   = client.Order.Order_getOrders(symbol="ETHUSD").result()
    # 현재 시장가격
    marketPrice = float(client.Market.Market_orderbook(symbol="ETHUSD").result()[0]['result'][0]['price'])
    # 현재 포지션 주문 수량
    buySize     = int(pos[0]['result']['size'])
    
    if pos[0]['result']['side'] == "Sell" :
        print("AS")

    elif pos[0]['result']['side'] == "Buy" :
        # 지정가 예약 취소
        client.Order.Order_cancelAll(symbol="ETHUSD").result()
        # 매수 잔량 청산
        client.Order.Order_new(side="Sell",symbol="ETHUSD",order_type="Market",qty=int(buySize),time_in_force="GoodTillCancel").result()
        # 시장가 매수
        client.Order.Order_new(side="Sell",symbol="ETHUSD",order_type="Market",qty=4,time_in_force="GoodTillCancel").result()
        # 시장가 - 1000 지정가 예약
        client.Order.Order_new(side="Buy",symbol="ETHUSD",order_type="Limit",qty=2,price=marketPrice-1000,time_in_force="GoodTillCancel").result()
        # 시장가 - 2000 지정가 예약
        client.Order.Order_new(side="Buy",symbol="ETHUSD",order_type="Limit",qty=1,price=marketPrice-2000,time_in_force="GoodTillCancel").result()
        # 시장가 - 3000 지정가 예약
        client.Order.Order_new(side="Buy",symbol="ETHUSD",order_type="Limit",qty=1,price=marketPrice-3000,time_in_force="GoodTillCancel").result()
        
        print("LS",",",marketPrice, buySize)
    else :
        # 지정가 예약 취소
        client.Order.Order_cancelAll(symbol="ETHUSD").result()
        # 시장가 매수
        client.Order.Order_new(side="Sell",symbol="ETHUSD",order_type="Market",qty=4,time_in_force="GoodTillCancel").result()
        # 시장가 - 1000 지정가 예약
        client.Order.Order_new(side="Buy",symbol="ETHUSD",order_type="Limit",qty=2,price=marketPrice-1000,time_in_force="GoodTillCancel").result()
        # 시장가 - 2000 지정가 예약
        client.Order.Order_new(side="Buy",symbol="ETHUSD",order_type="Limit",qty=1,price=marketPrice-2000,time_in_force="GoodTillCancel").result()
        # 시장가 - 3000 지정가 예약
        client.Order.Order_new(side="Buy",symbol="ETHUSD",order_type="Limit",qty=1,price=marketPrice-3000,time_in_force="GoodTillCancel").result()
        
        print("SE",",",marketPrice, buySize)

sell_process()