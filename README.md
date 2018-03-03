# github_trending_android

An Android port of [github_trending_ios](https://github.com/onepointsixtwo/github_trending_ios). This repository displays a list of currently trending GitHub repositories and an informational page displaying the Readme.md for each.

## Arch

A hybrid of MVVM and MVP to allow for user actions with callbacks via a protocol for simplicity, but data binding used anywhere it can sensibly be used.

## Design

The design is not the exact same as that in the iOS version, but instead is more idiomatically an Android version. The original designs were for an iOS app so I allowed for differences where it was sensible.
