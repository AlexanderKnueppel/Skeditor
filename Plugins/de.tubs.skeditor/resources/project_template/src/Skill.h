//
// Created by metin on 16.11.20.
//

#ifndef ROSPROJECT_SKILL_H
#define ROSPROJECT_SKILL_H
#include <string>
#include "ros/ros.h"
#include "std_msgs/Float64.h"
#include <list>

#define EP 0.1

ros::Rate* loopRate;
ros::NodeHandle* nodeHandle;

void init(int argc, char **argv, const std::string& name){
    ros::init(argc, argv, name);

    // we wont free it, up, because it should stay alive the whole lifetime of the process
    nodeHandle = new ros::NodeHandle();
    loopRate = new ros::Rate(1/EP);

}

void init(const std::string& name){
    init(0, 0, name);
}



class InputVariable{
public:
    void onRecv(const std_msgs::Float64& v){
        *this->var = v.data;
    }
    InputVariable (const std::string& name, long double* var) : var(var) {
        subscriber = nodeHandle->subscribe(name, 1, &InputVariable::onRecv, this);
        this->var = var;
    }
private:
    ros::Subscriber subscriber;
    long double* var;
};



class OutputVariable{
public:
    OutputVariable (const std::string& name, const long double* var) : var(var){
        publisher = nodeHandle->advertise<std_msgs::Float64>(name, 1);
    }

    void publishValue(){
        std_msgs::Float64 msg;
        msg.data = *var;
        publisher.publish(msg);
    }

private:
    ros::Publisher publisher;
    const long double* var;
};


std::list<InputVariable*> inputVariables = {};
std::list<OutputVariable*> outputVariables = {};

bool loop(){
    // publish all outputvariables
    for(OutputVariable* outputVariable : outputVariables){
        outputVariable->publishValue();
    }

    loopRate->sleep();
    ros::spinOnce();

    if(ros::ok()){
        return true;
    }else{
        //clear input and output variables
        for(OutputVariable* outputVariable : outputVariables) {
            free(outputVariable);
        }
        outputVariables.clear();

        for(InputVariable* inputVariable : inputVariables) {
            free(inputVariable);
        }
        inputVariables.clear();
        return false;
    }
}

void registerInputVar(const std::string& name, long double* var) { 
    inputVariables.push_back(new InputVariable(name, var)); 
}
void registerOutputVar(const std::string& name, long double* var) { 
    outputVariables.push_back(new OutputVariable(name, var)); 
}

#endif //ROSPROJECT_SKILL_H