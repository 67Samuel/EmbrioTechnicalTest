# EmbrioTechnicalTest

The app has the required architecture for a Room database, but saving the data to the database was not implemented due to lack of time.
It uses MVI architecture.

## How to build/run/test
- Open project in Android Studio
- Connect a device to it or run an emulator
> Tested on OnePlus 5T

### Give some thought on how you can design a better user experience for Phone and Tablet users.
- Obviously the UI can be improved - currently the app does not use different themes for light and dark/day and night mode, and does not handle the errors in a graceful and specific manner.
- The app also shows only one weather forecast per day, and so a way to see a more granular report would increase user experience.
- The ability for the device to detect the location of the user and report the weather forecast based on that would also be required.
- The ability to choose a country or city from which to get the weather forecast might also be a nice touch, though arguably it might be a scarcely used feature that would decrease user experience given the added complexity of the resulting app.

### What I would do if I had more time
- Implement the ability to detect the device's location and base the API call off that. I would probably use FusedLocationProvider.
- Make use of all the weather data provided for each day. I was thinking of having the times for different days in the recyclerview and swiping right or left on the main screen would bring the user to different days.
- Improve the color scheme, themes, and UI in general.
- Integrate the Room database into the data flow, using the app's data storage as the single source of truth.
