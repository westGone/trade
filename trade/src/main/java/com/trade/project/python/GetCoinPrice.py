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
def get_price() :
    
    client = bybit.bybit(test=False, api_key='vdxzqZLxzJtJaemOgX', api_secret='Xs2atrYxi1llNO3mDHqTTMYKrO0nS5doPzBx')
    
    priceResult = client.Market.Market_symbolInfo().result()[0]['result'];
    df = pd.DataFrame(priceResult);
    print(priceResult)
    print(df['last_price'][0],",",df['price_24h_pcnt'][0],",",df['last_price'][1],",",df['price_24h_pcnt'][1],",",df['last_price'][2],",",df['price_24h_pcnt'][2],",",df['last_price'][3],",",df['price_24h_pcnt'][3])
    sys.exit(220)
    
get_price()
