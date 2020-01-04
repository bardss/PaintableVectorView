# PaintableVectorView
[![](https://jitpack.io/v/bardss/PaintableVectorView.svg)](https://jitpack.io/#bardss/PaintableVectorView)

PaintableVectorView enables to change color of paths/groups in Vector Drawable (SVG)

## Demo

![Alt Text](https://i.imgur.com/1CLBhXC.gif)
<div>Car icon made by <a href="https://www.flaticon.com/authors/prosymbols" title="Prosymbols">Prosymbols</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>

## Dependency

Add it in your root build.gradle at the end of repositories:
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
    implementation 'com.github.bardss:PaintableVectorView:1.0.0'
}
```

## Usage

Create PaintableVectorView
```kotlin
val paintableView = PaintableVectorView(
    context = this,
    drawableId = R.drawable.ic_car,
    paintType = PaintType.PAINT_GROUP,
    paintColor = resources.getColor(R.color.blue)
)
```


Set other paint type:
```kotlin
paintableView.paintType = PaintType.PAINT_PATH
```


Set other paint color:
```kotlin
paintableView.paintType = PaintType.PAINT_GROUP
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
