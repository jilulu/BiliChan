# BiliChan

![Screenshot1](http://jilulu.github.io/feature.png)

### BiliChan is an unofficial client for [KonaChan](konachan.com) 

You can explore and discover the pictures of your favorite anime character (be it Saber or Misaka Mikoto), 
download your beloved images to the local storage of your phone or star it in the app for later access. 

### [Obtain .apk installer](https://raw.githubusercontent.com/jilulu/BiliChan/master/app/app-release.apk)
### Build it yourself
###### Option 1 (Recommended): Android Studio
Check out from version control -> Git -> Paste `https://github.com/jilulu/BiliChan.git` into "Git Repository URL". 
###### Option 2: Gradle
```bash
git clone https://github.com/jilulu/BiliChan.git && cd BiliChan
./gradlew build
./gradlew assemble
```
Then two apk packages `app-debug-unaligned.apk` and `app-release-unsigned.apk` should be available in `BiliChan/build/apk/`. 
