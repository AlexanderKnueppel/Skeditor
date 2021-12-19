# Case Study: Vehicle Follow Mode

Files can be found here: [Examples/FollowMode-CaseStudy2021](https://github.com/AlexanderKnueppel/Skeditor/blob/develop/Plugins/de.tubs.skeditor.examples/skeditor_examples/FollowMode-CaseStudy2021)

### Example Hybrid Mode Automaton for Tempo Limit:

[Image of skill graph](https://github.com/AlexanderKnueppel/Skeditor/blob/develop/Plugins/de.tubs.skeditor.examples/skeditor_examples/FollowMode-CaseStudy2021/src/graphs/ControlLong-G1.png)

[Textual description](https://github.com/AlexanderKnueppel/Skeditor/blob/develop/Plugins/de.tubs.skeditor.examples/skeditor_examples/FollowMode-CaseStudy2021/src/SL-programs/Example_TempoLimit-G1.txt)

[KeYmaera X problem file](https://github.com/AlexanderKnueppel/Skeditor/blob/develop/Plugins/de.tubs.skeditor.examples/skeditor_examples/FollowMode-CaseStudy2021/src/Monolithic-Maneuvers-DL/ControlLong-G1-TempoLimit-Monolithic.kyx)

More information coming soon.

# Skeditor v.0.1 (alpha)

<img align="left" alt="Skillgraph" src="/skillgraph.png" width="25%">

Skeditor is a framework to model provably correct (driving) maneuvers based on skill graphs and hybrid programs. Provably correct means that an adequate maneuver should never violate any identified safety requirements during its execution.
Skills (nodes) in a skill graph are component abstractions that can either be part of the input/source (sensors and data processing), the actual controller, or the output/sink (actuators).
Each of these abstractions can be specified with FOL contracts to enable modular formal verification. Based on the global contract (precondition and postcondition) of the modeled maneuver, overall safety correctness of the maneuver can be established  _in the lab_ at design time! 

This project focueses on _prototyping_ such maneuvers to quickly evaluate the quality of the specification. Goal is to increase trust in the safety of such maneuvers to a maximal degree. This includes:
* Analyzing well-formedness of the models,
* verifying each skill individually and the complete maneuver to establish correctness proofs,
* but also to simulate the modeled maneuver using ROS or AirSim, to see whether the correct specification is enough to be actually safe.

<p align="center">
<img alt="Driving maneuver" src="/maneuver.png" width="80%">
</p>

Due to their compositional nature, skills and verification results can be reused accross driving maneuvers, making them good candidates for integrating them into a development process.

## Getting Started

Skeditor is a set of Eclipse-Plugins based on graphiti and EMF. **(tbd)**

## Publications

**Skill-based Verification of Cyber-Physical Systems**  
Alexander Knüppel, Inga Jatzkowski, Marcus Nolte, Thomas Thüm, Tobias Runge, and Ina Schaefer
Fundamental Approaches to Software Engineering (FASE). Springer, 2020.
[[pdf](https://link.springer.com/chapter/10.1007/978-3-030-45234-6_10)]
