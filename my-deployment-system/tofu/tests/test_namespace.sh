#!/bin/bash

set -e
kubectl get ns infra
kubectl get ns apps

echo "Both namespaces exist ✅"