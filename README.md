# Android-Fragment-Grid-Flip-Demo

Animates grid view items as they are added and each grid view item has a card flip animation.

This complete demo shows a GridView with custom Card Flip animations on each item in the list. Animations are applied by an onclick event attached to each GridView item View. Each item is quickly added to the list by a dummy thread in a worker fragment when the demo is started so the front of each card is unique. I have kept the back all the same for simplicity.

All screen orientation changes are handled gracefully by the application. If a card has been flipped it will stay flipped and each item will keep the displayed text on the front.

The worker thread pauses and resumes when screen is turned to avoid crashes.

This GridView card flip demo could easily be adapted to include images or any other type of view items required.
