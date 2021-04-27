# -*- coding: utf-8 -*-

import pandas as pd
import numpy as np
import plotly.graph_objects as go
import bybit
import time
import calendar
import sys

from pybit import HTTP
from datetime import datetime
from builtins import len

#print(client)
def cal_dmi() :
    session = HTTP(
        endpoint='https://api.bybit.com',
        api_key='vdxzqZLxzJtJaemOgX',
        api_secret='Xs2atrYxi1llNO3mDHqTTMYKrO0nS5doPzBx'
    )
    
    # 시간 Data 설정
    now      = datetime.utcnow()
    unixtime = calendar.timegm(now.utctimetuple())
    # 데이터의 Limit가 200이기 때문에 나눠서 받아온다.
    sinceArr = [14]
    # 데이터를 담아줄 Array 선언
    dfAllData = [0]
    # 당일의 최고가, 최저가, 마감가
    dfHigh  = [0]
    dfLow   = [0]
    dfClose = [0]

    for i in range(len(sinceArr)) :
        since = unixtime - 60 * 60 * 24 * int(sinceArr[i])
        # Data 호출
        response = session.query_kline(symbol='ETHUSD', interval=240, **{'from':since})['result']
        # Pandas Data 파싱
        df = pd.DataFrame(response)
        
        dfHigh.append(df["high"])
        dfLow.append(df["low"])
        dfClose.append(df["close"])
        
    del dfHigh[0]
    del dfLow[0]
    del dfClose[0]
    
    UpI = [0]
    DoI = [0]
    for i in range(len(sinceArr)) : 
        for k in range(len(dfHigh[i])-1) : 
            UpMove = float(dfHigh[i][k+1]) - float(dfHigh[i][k]) 
            DoMove = float(dfLow[i][k]) - float(dfLow[i][k+1])
            
            if UpMove > DoMove and UpMove > 0 : 
                UpD = UpMove * 100
            else : 
                UpD = 0 
            UpI.append(UpD) 
            
            if DoMove > UpMove and DoMove > 0 :
                DoD = DoMove * 100
            else :
                DoD = 0 
            DoI.append(DoD) 
    
    del UpI[0]
    del DoI[0]
    TR_l = [0]
    maxHigh = 0
    minLow  = 0
    #####
    trChoose = [0]
    client = bybit.bybit(test=False, api_key='vdxzqZLxzJtJaemOgX', api_secret='Xs2atrYxi1llNO3mDHqTTMYKrO0nS5doPzBx')
    trData = client.Kline.Kline_get(symbol="ETHUSD", interval="240", **{'from':unixtime-1209600}).result()[0]['result']

    for i in range(len(trData)) : 
        trCatch = [abs(float(trData[i]["high"])-float(trData[i]["low"])),
                   abs(float(trData[i]["high"])-float(trData[i]["open"])),
                   abs(float(trData[i]["low"])-float(trData[i]["open"]))]
        trChoose.append(np.max(trCatch))
        
    del trChoose[0]
    del trChoose[0]
    
    for i in range(len(UpI)) : 
        TR_l.append(trChoose[i])
    #####
    del TR_l[0]
    TR_s  = pd.Series(TR_l) 
    ATR   = pd.Series(TR_s.ewm(span=30, min_periods=1).mean())
    UpI   = pd.Series(UpI) 
    DoI   = pd.Series(DoI) 
    PosDI = pd.Series(UpI.ewm(span=30, min_periods=1).mean() / ATR) 
    NegDI = pd.Series(DoI.ewm(span=30, min_periods=1).mean() / ATR) 
    ADX   = pd.Series((abs(PosDI - NegDI) / (PosDI + NegDI)).ewm(span=60, min_periods=1).mean(), name='ADX_' + str(60) + '_' + str(60)) 

    # fig = go.Figure()
    # fig.add_trace(go.Scatter(x=UpI.index[10:], y=PosDI[10:], mode='lines', name="PDI"))
    # fig.add_trace(go.Scatter(x=UpI.index[10:], y=NegDI[10:], mode='lines', name="MDI"))
    # fig.add_trace(go.Scatter(x=UpI.index[10:], y=ADX[10:], mode='lines', name="ADX"))
    # fig.update_layout(title='DMI and ADX', xaxis_title='days', yaxis_title='Value')
    # fig.show()
    
    print(PosDI[len(PosDI)-2],",",PosDI[len(PosDI)-1],",",NegDI[len(NegDI)-2],",",NegDI[len(NegDI)-1],",",ADX[len(ADX)-3],",",ADX[len(ADX)-1])
  
    # # 데이터의 Limit가 200이기 때문에 나눠서 받아온다.
    # sinceArr = [14, 12, 10, 8, 6, 4, 2]
    # # 데이터를 담아줄 Array 선언
    # dfAllData = [0]
    # # 당일의 최고가, 최저가, 마감가
    # dfHigh  = [0]
    # dfLow   = [0]
    # dfClose = [0]    #
    #
    # for i in range(len(sinceArr)) :
        # since = unixtime - 60 * 60 * 24 * int(sinceArr[i])
        # # Data 호출
        # response = session.query_kline(symbol='BTCUSD', interval=15, **{'from':since})['result']
        # # Pandas Data 파싱
        # df = pd.DataFrame(response)
        # for k in range(len(df)) :
            # if k > 191 :
                # df= df.drop(k)
                #
        # dfHigh.append(df["high"])
        # dfLow.append(df["low"])
        # dfClose.append(df["close"])
        #
    # del dfHigh[0]
    # del dfLow[0]
    # del dfClose[0]
    #
    # UpI = [0]
    # DoI = [0]
    #
    # for i in range(len(sinceArr)) : 
        # for k in range(len(dfHigh[i])-1) : 
            # UpMove = float(dfHigh[i][k+1]) - float(dfHigh[i][k]) 
            # DoMove = float(dfLow[i][k]) - float(dfLow[i][k+1])
            #
            # if UpMove > DoMove and UpMove > 0 : 
                # UpD = UpMove * 100
            # else : 
                # UpD = 0 
            # UpI.append(UpD) 
            #
            # if DoMove > UpMove and DoMove > 0 :
                # DoD = DoMove * 100
            # else :
                # DoD = 0 
            # DoI.append(DoD) 
            #
    # del UpI[0]
    # del DoI[0]
    #
    # TR_l = [0]
    # maxHigh = 0
    # minLow  = 0
    # for i in range(len(sinceArr)) : 
        # if float(maxHigh) < float(np.max(dfHigh)) :
            # maxHigh = np.max(dfHigh)
            #
        # if float(minLow) < float(np.min(dfLow)) :
            # minLow = np.min(dfLow)
            #
    # for i in range(len(UpI)) : 
        # TR_l.append(abs(float(maxHigh)-float(minLow)))
        #
    # del TR_l[0]
    # TR_s  = pd.Series(TR_l) 
    # ATR   = pd.Series(TR_s.ewm(span=14, min_periods=1).mean())
    # UpI   = pd.Series(UpI) 
    # DoI   = pd.Series(DoI) 
    # PosDI = pd.Series(UpI.ewm(span=14, min_periods=1).mean() / ATR) 
    # NegDI = pd.Series(DoI.ewm(span=14, min_periods=1).mean() / ATR) 
    # ADX   = pd.Series((abs(PosDI - NegDI) / (PosDI + NegDI)).ewm(span=14, min_periods=1).mean(), name='ADX_' + str(14) + '_' + str(14)) 
    #
    # # fig = go.Figure()
    # # fig.add_trace(go.Scatter(x=UpI.index[10:], y=PosDI[10:], mode='lines', name="PDI"))
    # # fig.add_trace(go.Scatter(x=UpI.index[10:], y=NegDI[10:], mode='lines', name="MDI"))
    # # fig.add_trace(go.Scatter(x=UpI.index[10:], y=ADX[10:], mode='lines', name="ADX"))
    # # fig.update_layout(title='DMI and ADX', xaxis_title='days', yaxis_title='Value')
    # # fig.show()
    #
    # print(PosDI[len(PosDI)-2],",",PosDI[len(PosDI)-1],",",NegDI[len(NegDI)-2],",",NegDI[len(NegDI)-1],",",ADX[len(ADX)-3],",",ADX[len(ADX)-1])
    
    sys.exit(220)
    
cal_dmi()
