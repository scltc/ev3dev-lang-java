# ev3dev-lang-java [![Build Status](https://travis-ci.org/mob41/ev3dev-lang-java.svg?branch=master)](https://travis-ci.org/mob41/ev3dev-lang-java) [![CodeFactor](https://www.codefactor.io/repository/github/mob41/ev3dev-lang-java/badge)](https://www.codefactor.io/repository/github/mob41/ev3dev-lang-java)

A light-weight but powerful ev3dev unified language binding for Java, not to be confused with [jabrena/ev3dev-lang-java](https://github.com/ev3dev-lang-java/ev3dev-lang-java/) which interfaces LeJOS. It follows with the [language wrapper specification](http://ev3dev-lang.readthedocs.org/en/latest/spec.html).

**No idea where to start?** Check the [getting started](https://mob41.github.io/ev3dev-lang-java/getting_started.html) guide!

**How to add as Maven dependency?** Take a look at [this](https://mob41.github.io/ev3dev-lang-java/getting_started-adding_maven.html).

## Compatibility

This library usually supports the latest ev3dev kernel, unless a "breaking changes" kernel is released. This library is tested to run with ev3dev kernel version 20.

- ```v4.4.61-20-ev3dev-ev3``` for EV3
- ```v4.4.61-ti-rt-r98-20-ev3dev-bb.org``` for BeagleBone
- ```v4.4.61-20-ev3dev-rpi``` for Raspberry Pi 0/1
- ```v4.4.61-20-ev3dev-rpi2``` for Raspberry Pi 2/3

This library support most of the motors and EV3-G sensors. Extra bindings are still in development, for details check [here](https://mob41.github.io/ev3dev-lang-java/guide-extra_bindings.html).

## License

[tl;dr](https://tldrlegal.com/license/mit-license) This project is licensed under the MIT License, whereas any other applications/libraries copy or base their code on this is a kind of "modified" work.