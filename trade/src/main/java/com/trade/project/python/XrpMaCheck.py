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


#client = bybit.bybit(test=False, api_key='vdxzqZLxzJtJaemOgX', api_secret='Xs2atrYxi1llNO3mDHqTTMYKrO0nS5doPzBx')
#print(client)
def cal_ma() :
    # 시간 Data 설정
    now      = datetime.utcnow()
    unixtime = calendar.timegm(now.utctimetuple())
    # 20일치의 Data를 40번에 나눠서 받아온다.
    sinceArr = [1728000, 1684800, 1641600, 1598400, 1555200, 1512000, 1468800, 1425600, 1382400, 1339200,
                1296000, 1252800, 1209600, 1166400, 1123200, 1080000, 1036800, 993600 , 950400 , 907200 ,
                864000 , 820800 , 777600 , 734400 , 691200 , 648000 , 604800 , 561600 , 518400 , 475200 ,
                432000 , 388800 , 345600 , 302400 , 259200 , 216000 , 172800 , 129600 , 86400  , 43200]
    # 데이터를 담아줄 Array 선언
    dfAllData = [0]
    # 당일의 최고가, 최저가, 마감가
    dfHigh  = [0]
    dfLow   = [0]
    dfClose = [0]
    
    client = bybit.bybit(test=False, api_key='vdxzqZLxzJtJaemOgX', api_secret='Xs2atrYxi1llNO3mDHqTTMYKrO0nS5doPzBx')
    
    for i in range(len(sinceArr)) :
        response = client.Kline.Kline_get(symbol="XRPUSD", interval="5", **{'from':unixtime-sinceArr[i]}, limit=144).result()[0]['result']
        # Pandas Data 파싱
        
        for k in range(len(response)) :
            dfClose.append(response[k]["close"])
    
    del dfClose[0]
    
    pdClosd = pd.Series(dfClose)
    ma20 = pd.Series(pdClosd.ewm(span=20, min_periods=1).mean()) 
    ma5  = pd.Series(pdClosd.ewm(span=5, min_periods=1).mean()) 

    # fig = go.Figure()
    # fig.add_trace(go.Scatter(x=pdClosd.index[10:], y=ma20[10:], mode='lines', name="20"))
    # fig.add_trace(go.Scatter(x=pdClosd.index[10:], y=ma5[10:], mode='lines', name="5"))
    # fig.update_layout(title='DMI and ADX', xaxis_title='days', yaxis_title='Value')
    # fig.show()
    print(ma5[len(ma5)-2],",",ma5[len(ma5)-1],",",ma20[len(ma20)-2],",",ma20[len(ma20)-1])
    
    sys.exit(220)
    
cal_ma()
