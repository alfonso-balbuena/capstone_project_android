# Capstone project for the android developer degree in udacity.

App to show the tourist places that are close to you. The user can create new routes with places to follow.
Moreover the user can see the detail of a certain place either way click in the window information in the map or in the tab of my places

## Api key

To get an api key for Google follow [getting api key](https://developers.google.com/places/android-sdk/get-api-key)

Note: this project uses the next Google Apis

- Google Maps
- Google Geolocation
- Google Places

Once you have an api key, put the api key in the file res/values/google_maps_api.xml as the string google_maps_key.

## Screenshots

### Common


<img src="/img/home.png" alt="drawing" width="250"/>  <img src="/img/click_place.png" alt="drawing" width="250"/> 

<img src="/img/my_routes.png" alt="drawing" width="250"/> <img src="/img/route.png" alt="drawing" width="250"/> <img src="/img/my_places.png" alt="drawing" width="250"/>

### Free version

<img src="/img/free_detail.png" alt="drawing" width="250"/>

### Pay version

<img src="/img/paid_detail.png" alt="drawing" width="250"/>

## Note

The app uses a workmanager to delete all the information in the database in order to follow the terms and conditions of Google APIs
