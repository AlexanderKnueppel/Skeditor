#ifndef AIRSIM_SKELETTON_SKILL_H
#define AIRSIM_SKELETTON_SKILL_H

#include <mutex>
#include <shared_mutex>

#define EP 0.1

bool loop() {
    std::this_thread::sleep_for(std::chrono::milliseconds((long)(1/EP)));
    return true;
}

std::mutex carControlsLock;

void setThrottle(msr::airlib::CarRpcLibClient* client, float value, bool forward = true) {
    std::unique_lock<std::mutex> lock(carControlsLock);
    auto controls = client->getCarControls();
    controls.set_throttle(value, forward);
    client->setCarControls(controls);
}

void setSteering(msr::airlib::CarRpcLibClient* client, float value) {
    std::unique_lock<std::mutex> lock(carControlsLock);
    auto controls = client->getCarControls();
    controls.steering = value;
    client->setCarControls(controls);
}

void setBrake(msr::airlib::CarRpcLibClient* client, float value) {
    std::unique_lock<std::mutex> lock(carControlsLock);
    auto controls = client->getCarControls();
    controls.brake = value;
    client->setCarControls(controls);
}

void setHandbrake(msr::airlib::CarRpcLibClient* client, bool value) {
    std::unique_lock<std::mutex> lock(carControlsLock);
    auto controls = client->getCarControls();
    controls.handbrake = value;
    client->setCarControls(controls);
}

void setIsManualGear(msr::airlib::CarRpcLibClient* client, bool value = true) {
    std::unique_lock<std::mutex> lock(carControlsLock);
    auto controls = client->getCarControls();
    controls.is_manual_gear = value;
    client->setCarControls(controls);
}

void setManualGear(msr::airlib::CarRpcLibClient* client, int value) {
    std::unique_lock<std::mutex> lock(carControlsLock);
    auto controls = client->getCarControls();
    controls.is_manual_gear = true;
    controls.manual_gear = value;
    client->setCarControls(controls);
}

void setGearImmediate(msr::airlib::CarRpcLibClient* client, bool value = true) {
    std::unique_lock<std::mutex> lock(carControlsLock);
    auto controls = client->getCarControls();
    controls.gear_immediate = value;
    client->setCarControls(controls);
}

float getSpeed(msr::airlib::CarRpcLibClient* client) {
    auto v = client->getCarState().speed / 10;
    //std::cout << "speed: " << v << std::endl;
    return v;
}

int getGear(msr::airlib::CarRpcLibClient* client) {
    return client->getCarState().gear;
}

float getRpm(msr::airlib::CarRpcLibClient* client) {
    return client->getCarState().rpm;
}

float getMaxRpm(msr::airlib::CarRpcLibClient* client) {
    return client->getCarState().maxrpm;
}

bool getHandbrake(msr::airlib::CarRpcLibClient* client) {
    return client->getCarState().handbrake;
}

#endif //AIRSIM_SKELETTON_SKILL_H
