# PaintableVectorView
[![](https://jitpack.io/v/bardss/PaintableVectorView.svg)](https://jitpack.io/#bardss/PaintableVectorView)

PaintableVectorView enables to change color of paths/groups in Vector Drawable (SVG)

## Demo

![Alt Text](https://i.imgur.com/1CLBhXC.gif)
<div>Car icon made by <a href="https://www.flaticon.com/authors/prosymbols" title="Prosymbols">Prosymbols</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>

## Dependency

Add the following lines in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Add the dependency
```
dependencies {
    implementation 'com.github.bardss:PaintableVectorView:1.0.4'
}
```

## Usage

Create PaintableVectorView and add to the layout:
```kotlin
val paintableView = PaintableVectorView(
    context = this,
    drawableId = R.drawable.ic_car,
    paintType = PaintType.PAINT_PATH,
    paintColor = resources.getColor(R.color.blue)
)
layout.addView(paintableView)
```

Or add in xml and set attributes:
```xml
    <com.jakubaniola.paintablevectorview.PaintableVectorView
        android:id="@+id/paintableView"
        android:layout_width="200dp"
        android:layout_height="200dp"
        android:layout_margin="18dp"
        app:drawable="@drawable/ic_car"
        app:paintColor="@color/blue"
        app:paintType="GROUP" />
```

Set other paint type:
```kotlin
paintableView.paintType = PaintType.PAINT_GROUP
```

```xml
app:paintType="GROUP"
```
```xml
app:paintType="PATH"
```

Set other paint color:
```kotlin
paintableView.paintColor = resources.getColor(R.color.blue)
```

```xml
app:paintColor="@color/blue"
```


Reset layers color in PaintableVectorView:
```kotlin
paintableView.resetColors()
```

## License

```
Copyright 2019 Jakub Aniola

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
