#!/bin/bash
# ============================================================
#  ClassMate3000 - Seed Script (Tasks + Reminders via API)
#  Run this script ONCE against the live deployed services.
#  Usage: bash scripts/seed_mongo.sh
# ============================================================

BASE_TASK="https://classmate3000-api-gateway.onrender.com/api/v1/tasks"
BASE_REMINDER="https://classmate3000-api-gateway.onrender.com/api/v1/reminders"

echo "Seeding Tasks..."

# ---- COURSE 1: COMP3095 (courseId=1) ----

T1=$(curl -s -X POST $BASE_TASK \
  -H "Content-Type: application/json" \
  -d '{
    "courseId": 1,
    "title": "Capstone Sprint 1 Deliverable",
    "type": "PROJECT",
    "dueDate": "2026-03-01T23:59:00",
    "isCompleted": true,
    "isBonus": false,
    "isPriority": false,
    "priorityThresholdDays": 3,
    "manualPriorityOverride": false,
    "weight": 15.0,
    "scorePercent": 88.0
  }')
echo "Task 1: $T1"
T1_ID=$(echo $T1 | grep -o '"taskId":[0-9]*' | grep -o '[0-9]*')

T2=$(curl -s -X POST $BASE_TASK \
  -H "Content-Type: application/json" \
  -d '{
    "courseId": 1,
    "title": "Architecture Diagram Submission",
    "type": "ASSIGNMENT",
    "dueDate": "2026-03-15T23:59:00",
    "isCompleted": true,
    "isBonus": false,
    "isPriority": false,
    "priorityThresholdDays": 3,
    "manualPriorityOverride": false,
    "weight": 10.0,
    "scorePercent": 92.0
  }')
echo "Task 2: $T2"
T2_ID=$(echo $T2 | grep -o '"taskId":[0-9]*' | grep -o '[0-9]*')

T3=$(curl -s -X POST $BASE_TASK \
  -H "Content-Type: application/json" \
  -d '{
    "courseId": 1,
    "title": "Capstone Sprint 2 Deliverable",
    "type": "PROJECT",
    "dueDate": "2026-04-05T23:59:00",
    "isCompleted": false,
    "isBonus": false,
    "isPriority": true,
    "priorityThresholdDays": 5,
    "manualPriorityOverride": false,
    "weight": 20.0,
    "scorePercent": 0.0
  }')
echo "Task 3: $T3"
T3_ID=$(echo $T3 | grep -o '"taskId":[0-9]*' | grep -o '[0-9]*')

T4=$(curl -s -X POST $BASE_TASK \
  -H "Content-Type: application/json" \
  -d '{
    "courseId": 1,
    "title": "Midterm Exam",
    "type": "EXAM",
    "dueDate": "2026-03-20T10:00:00",
    "isCompleted": true,
    "isBonus": false,
    "isPriority": false,
    "priorityThresholdDays": 7,
    "manualPriorityOverride": false,
    "weight": 25.0,
    "scorePercent": 78.0
  }')
echo "Task 4: $T4"
T4_ID=$(echo $T4 | grep -o '"taskId":[0-9]*' | grep -o '[0-9]*')

# ---- COURSE 2: COMP3133 (courseId=2) ----

T5=$(curl -s -X POST $BASE_TASK \
  -H "Content-Type: application/json" \
  -d '{
    "courseId": 2,
    "title": "REST API Lab",
    "type": "LAB",
    "dueDate": "2026-03-05T23:59:00",
    "isCompleted": true,
    "isBonus": false,
    "isPriority": false,
    "priorityThresholdDays": 3,
    "manualPriorityOverride": false,
    "weight": 10.0,
    "scorePercent": 95.0
  }')
echo "Task 5: $T5"
T5_ID=$(echo $T5 | grep -o '"taskId":[0-9]*' | grep -o '[0-9]*')

T6=$(curl -s -X POST $BASE_TASK \
  -H "Content-Type: application/json" \
  -d '{
    "courseId": 2,
    "title": "GraphQL Quiz",
    "type": "QUIZ",
    "dueDate": "2026-03-12T09:00:00",
    "isCompleted": true,
    "isBonus": false,
    "isPriority": false,
    "priorityThresholdDays": 3,
    "manualPriorityOverride": false,
    "weight": 5.0,
    "scorePercent": 80.0
  }')
echo "Task 6: $T6"
T6_ID=$(echo $T6 | grep -o '"taskId":[0-9]*' | grep -o '[0-9]*')

T7=$(curl -s -X POST $BASE_TASK \
  -H "Content-Type: application/json" \
  -d '{
    "courseId": 2,
    "title": "Full Stack Final Project",
    "type": "PROJECT",
    "dueDate": "2026-04-20T23:59:00",
    "isCompleted": false,
    "isBonus": false,
    "isPriority": true,
    "priorityThresholdDays": 7,
    "manualPriorityOverride": false,
    "weight": 30.0,
    "scorePercent": 0.0
  }')
echo "Task 7: $T7"
T7_ID=$(echo $T7 | grep -o '"taskId":[0-9]*' | grep -o '[0-9]*')

T8=$(curl -s -X POST $BASE_TASK \
  -H "Content-Type: application/json" \
  -d '{
    "courseId": 2,
    "title": "React Component Assignment",
    "type": "ASSIGNMENT",
    "dueDate": "2026-03-25T23:59:00",
    "isCompleted": true,
    "isBonus": false,
    "isPriority": false,
    "priorityThresholdDays": 3,
    "manualPriorityOverride": false,
    "weight": 15.0,
    "scorePercent": 90.0
  }')
echo "Task 8: $T8"
T8_ID=$(echo $T8 | grep -o '"taskId":[0-9]*' | grep -o '[0-9]*')

echo ""
echo "Seeding Reminders..."

curl -s -X POST $BASE_REMINDER \
  -H "Content-Type: application/json" \
  -d "{
    \"taskId\": $T3_ID,
    \"message\": \"Sprint 2 due soon! Don't forget to push your final commits.\",
    \"scheduledAt\": \"2026-04-03T09:00:00\",
    \"wasSent\": false
  }" && echo ""

curl -s -X POST $BASE_REMINDER \
  -H "Content-Type: application/json" \
  -d "{
    \"taskId\": $T7_ID,
    \"message\": \"Full Stack Final Project deadline is approaching!\",
    \"scheduledAt\": \"2026-04-17T09:00:00\",
    \"wasSent\": false
  }" && echo ""

curl -s -X POST $BASE_REMINDER \
  -H "Content-Type: application/json" \
  -d "{
    \"taskId\": $T4_ID,
    \"message\": \"Midterm tomorrow - review your microservices notes!\",
    \"scheduledAt\": \"2026-03-19T18:00:00\",
    \"wasSent\": true
  }" && echo ""

curl -s -X POST $BASE_REMINDER \
  -H "Content-Type: application/json" \
  -d "{
    \"taskId\": $T5_ID,
    \"message\": \"REST API Lab submission reminder.\",
    \"scheduledAt\": \"2026-03-04T20:00:00\",
    \"wasSent\": true
  }" && echo ""

echo ""
echo "Seeding complete! Check your services."
