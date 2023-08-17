#!/bin/bash

c=$1

echo "your_password" | sudo -S shutdown -s +$(($c - 0))
