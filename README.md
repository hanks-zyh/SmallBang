# SmallBang

twitter like animation for any view :heartbeat:

![demo](https://github.com/hanks-zyh/SmallBang/blob/master/screenshots/demo.gif)

[Demo APK](https://github.com/hanks-zyh/SmallBang/blob/master/screenshots/demo.apk?raw=true)

## Usage

```groovy
dependencies {
    implementation 'pub.hanks:smallbang-library:1.2.0'
}
```

```xml
<xyz.hanks.library.bang.SmallBangView
    android:id="@+id/like_heart"
    android:layout_width="56dp"
    android:layout_height="56dp">

    <ImageView
        android:id="@+id/image"
        android:layout_width="20dp"
        android:layout_height="20dp"
        android:layout_gravity="center"
        android:src="@drawable/heart_selector"
        android:text="Hello World!"/>
</xyz.hanks.library.bang.SmallBangView>
```
or

```xml

<xyz.hanks.library.bang.SmallBangView
    android:id="@+id/like_text"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:circle_end_color="#ffbc00"
    app:circle_start_color="#fa9651"
    app:dots_primary_color="#fa9651"
    app:dots_secondary_color="#ffbc00">

    <TextView
        android:id="@+id/text"
        android:layout_width="50dp"
        android:layout_height="20dp"
        android:layout_gravity="center"
        android:gravity="center"
        android:text="hanks"
        android:textColor="@color/text_selector"
        android:textSize="14sp"/>
</xyz.hanks.library.bang.SmallBangView>
```
## Donate

If this project help you reduce time to develop, you can give me a cup of coffee :)

[![paypal](https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=UGENU2RU26RUG)

![](http://ww1.sinaimg.cn/large/8c9b876fly1flqz53k8j5j212b0kkq7p.jpg)


## Contact & Help

Please fell free to contact me if there is any problem when using the library.

- **email**: zhangyuhan2014@gmail.com
- **twitter**: https://twitter.com/zhangyuhan3030
- **weibo**: http://weibo.com/hanksZyh
- **blog**: http://hanks.pub

welcome to commit [issue](https://github.com/hanks-zyh/SmallBang/issues) & [pr](https://github.com/hanks-zyh/SmallBang/pulls)


---
## License

This library is licensed under the [Apache Software License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

See [`LICENSE`](LICENSE) for full of the license text.

    Copyright (C) 2015 [Hanks](https://github.com/hanks-zyh)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
