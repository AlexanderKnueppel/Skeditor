cmake_minimum_required(VERSION 3.0.2)
project(SKILL_NAME)

find_package(catkin REQUIRED COMPONENTS
  roscpp
  rospy
  std_msgs
  gazebo_ros
)

find_package(gazebo REQUIRED)

include_directories(include ${catkin_INCLUDE_DIRS} ${GAZEBO_INCLUDE_DIRS} ${SDFormat_INCLUDE_DIRS})

catkin_package(
        DEPENDS
            gazebo_ros
        CATKIN_DEPENDS
        INCLUDE_DIRS
        LIBRARIES
)

include_directories(
  ${catkin_INCLUDE_DIRS}
)

add_executable(SKILL_NAME src/main.cpp)
target_compile_features(SKILL_NAME PRIVATE cxx_std_20)
target_link_libraries(SKILL_NAME ${catkin_LIBRARIES})
