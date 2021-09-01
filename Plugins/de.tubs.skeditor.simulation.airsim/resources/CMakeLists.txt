cmake_minimum_required(VERSION 3.5.0)
project(airsim_skeletton)

# download airsim src and link to cmake folder
# run build.cmd from vs cmd

set(AIRSIM_DIR "XXX_AIRSIM_FOLDER_XXX")

IF(NOT UNIX)
    #set(CMAKE_GENERATOR_PLATFORM Win64)
    set(MyArch x64)
ENDIF()

LIST(APPEND CMAKE_MODULE_PATH "${AIRSIM_DIR}/cmake/cmake-modules")
INCLUDE("${AIRSIM_DIR}/cmake/cmake-modules/CommonSetup.cmake")

find_path(AIRSIM_ROOT NAMES AirSim.sln PATHS "${AIRSIM_DIR}" REQUIRED)
CommonSetup()

if("${BUILD_TYPE}" STREQUAL "release")
    message("this is a release build -> make sure that your AirLib and RPC used the same build options!")
elseif("${BUILD_TYPE}" STREQUAL "debug")
    message("this is a debug build -> make sure that your AirLib and RPC used the same build options!")
endif()

IF(NOT UNIX)
    message("windows build detected")
    if("${BUILD_TYPE}" STREQUAL "release")
        message("release build detected -> rewrite compiler flags because they are incompatible")
        # currently the windows cmake support has a bug and sets /ZI and /GL but this is incompatible and throws an error
        string(REPLACE "/GL " "" CMAKE_CXX_FLAGS ${CMAKE_CXX_FLAGS})
        # some how /W3 and /W4 are set so just remove /W3
        string(REPLACE "/W3 " "" CMAKE_CXX_FLAGS ${CMAKE_CXX_FLAGS})
        message("compiler flags: ${CMAKE_CXX_FLAGS}")
    endif()
ENDIF()

IncludeEigen()

SetupConsoleBuild()

## Specify additional locations of header files
include_directories(
        ${AIRSIM_ROOT}/AirLib/include
        ${RPC_LIB_INCLUDES}
        ${AIRSIM_ROOT}/MavLinkCom/include
        ${AIRSIM_ROOT}/MavLinkCom/common_utils
)

add_executable(${PROJECT_NAME} main.cpp)

#target_compile_features(${PROJECT_NAME} PRIVATE cxx_std_20)

#AddExecutableSource()
CommonTargetLink()

IF(NOT UNIX)
    target_link_libraries(${PROJECT_NAME} ${AIRSIM_DIR}/AirLib/lib/${MyArch}/Release/AirLib.lib)
    target_link_libraries(${PROJECT_NAME} ${AIRSIM_DIR}/AirLib/deps/rpclib/lib/${MyArch}/Release/rpc.lib)
ELSE()
    # lib linking for unix
ENDIF()