## acta (The traveller's best friend)

Acta is a third party mobile application which allows its users to record information about their journeys in the form of a journal with texts and pictures. It also provides the user with some features important to a traveller. This application will reduce the inconveniences experienced by users when traveling to new countries or when documenting journeys by providing them with some functions that needed several different applications beforehand.

# Scope of the App
The primary difference of acta from other travel blog applications is that it provides a currency converter, country information, and language tips in addition to the services other applications in the Play Store provide. The other functions include a travel blog, maps with directions, Google sign-in, and posting to Facebook and Twitter. 

# API used

- Google Places API 
    Google Places API was used to get Google maps, places autocomplete and places information. It was implemented by adding the API key in    the android manifest and calling PlacePicker 
- Facebook SDK
    Facebook SDK was used to share posts to Facebook through acta. Our app was set up with the Facebook Android SDK. 
- Firebase API 
    Firebase provided the Google Sign-in API 
- Fixer API 
    Fixer.io provided JSON API for currency conversion. Currency exchange rates could be obtained in JSON format and then they were   converted   to strings in our currency activity. Then they were used appropriately for calculations. 
- Picasso
    Library used to load and fit images in an image box.
