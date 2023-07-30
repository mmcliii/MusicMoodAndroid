import spotipy
import spotipy.util as util
import pandas as pd
import numpy
from spotipy.oauth2 import SpotifyClientCredentials
import numpy as np
import joblib
import sklearn
from os.path import dirname, join

scaler_file = join(dirname(__file__), "scaler_jlib.pkl")
model_file = join(dirname(__file__), "mlModel_jlib.pkl")

# #open scaler_file
# with open(scaler_file, 'r', encoding='ASCII', errors="ignore") as scaler_in:
#     scaler_data = scaler_in.read()
# #open model_file
# with open(model_file, 'r', encoding='ASCII', errors="ignore") as model_in:
#     model_data = model_in.read()

CLIENT_ID = "9979f20ecbf4411bafc84f236967dd7e"
CLIENT_SECRET = "16f2c288975747978e884dd4e79d822a"

auth_manager = SpotifyClientCredentials(CLIENT_ID, CLIENT_SECRET)
sp = spotipy.Spotify(auth_manager=auth_manager)


def get_features(track_id):
    # Create empty dataframe
    track_features_list = ["track_id", "danceability", "energy", "key", "loudness", "mode", "speechiness",
                           "instrumentalness", "liveness", "valence", "tempo"]
    track_df = pd.DataFrame(columns=track_features_list)

    # Create empty dict
    track_features = {}
    track_features["track_id"] = track_id

    audio_features = sp.audio_features(track_features["track_id"])[0]
    for feature in track_features_list[1:]:
        track_features[feature] = audio_features[feature]
    track_df = pd.DataFrame(track_features, index=[0])
    track_df.drop(['track_id'], axis=1, inplace=True)

    return track_df
my_df = get_features("33LC84JgLvK2KuW43MfaNq")

# robScale1 = pickle.load(open('robScale1.pkl', 'rb'))
robScale1 = joblib.load(scaler_file)
def convert_input(input_df):
    feature_np = input_df.to_numpy()
    X_input = robScale1.transform(feature_np)
    return X_input

# model = pickle.load(open('mlModel.pkl','rb'))
model = joblib.load(model_file)

def my_predict(track_id):
    feature_df = get_features(track_id)
    X_input = convert_input(feature_df)
    result = model.predict(X_input)[0]
    emotion = ""
    if(result == 1):
        emotion = "angry"
    elif(result == 2):
        emotion = "sad"
    elif(result == 3):
        emotion = "relaxed"
    else:
        emotion = "happy"
    return emotion

