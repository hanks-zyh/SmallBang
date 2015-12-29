# SmallBang
  twitter like animation for any view :heartbeat:


for **ImageView**

![ImageView](https://github.com/hanks-zyh/SmallBang/blob/master/screenshots/heart.gif)

for **Button**

![](https://github.com/hanks-zyh/SmallBang/blob/master/screenshots/button.gif)

for **TextView**

![TextView](https://github.com/hanks-zyh/SmallBang/blob/master/screenshots/text.gif)

for any?...

DEMO

![demo](https://github.com/hanks-zyh/SmallBang/blob/master/screenshots/demo.gif)


## Usage

### 0. Add the following to your build.gradle file.

```groovy
dependencies {
    compile 'hanks.xyz:smallbang-library:0.1.2'
}
```


### 1. init SmallBang
```java
mSmallBang = SmallBang.attach2Window(this);

```
### 2. animate to view
```java
mSmallBang.bang(view);
```
or

```java
mSmallBang.bang(view,new SmallBangListener() {
    @Override
    public void onAnimationStart() {
    }

    @Override
    public void onAnimationEnd() {
        toast("button +1");
    }
});
```
or

```java
mSmallBang.bang(view,50,new SmallBangListener() {
    @Override
    public void onAnimationStart() {
    }

    @Override
    public void onAnimationEnd() {
        toast("text+1");
    }
});
```

---
other method

```java
/**
  * set different colors for dots, the length should be 2 at least, colors[0] and colors[1] are Big Circle startColor and endColor;
  *
  * @param newColors
  */
 public void setColors(int[] newColors)

 /**
  * set small dot number
  * @param dotNumber
  */
 public void setDotNumber(int dotNumber);

```

## Contact & Help

Please fell free to contact me if there is any problem when using the library.

- **email**: zhangyuhan2014@gmail.com
- **twitter**: https://twitter.com/zhangyuhan3030
- **weibo**: http://weibo.com/hanksZyh
- **blog**: http://hanks.xyz

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
