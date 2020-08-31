# dspAlgorithmics

##What's all about.

What You see below is a waveform picture of a particular fragment of a drum loop import from a vinyl record using faulty cables.

<img src = "https://github.com/vitalispopoff/dspAlgorithmics/blob/gui/readme-resources/dirty-signal.PNG">

The result is a signal containing groups of random peaks contaminating the original signal.

<img src = "https://github.com/vitalispopoff/dspAlgorithmics/blob/gui/readme-resources/dirty-signal-01.PNG">

Although in the picture above the the peaks are presented as a quasi-sinusoidal impulses in reality they are a single samples which values significantly exceed the neighbouring signal, which becomes obvious when the zoom in preview reveals actuall locations of a particular samples.

<img src = "https://github.com/vitalispopoff/dspAlgorithmics/blob/gui/readme-resources/dirty-signal-02.PNG">

The main goal of the project is development of a Java based application to get rid of those peaks from the broken samples.      
