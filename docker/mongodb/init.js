// ===============================
// TASK SERVICE COLLECTION
// ===============================
db = db.getSiblingDB("taskdb");

// Task service uses @Document(collection="task"), so seed must write to "task" (not "tasks").
const taskCol = db.getCollection("task");

// Optional cleanup to avoid duplicate seeds on fresh init (safe on first run).
taskCol.deleteMany({});

taskCol.insertMany([
  {
    _id: NumberLong(1001), // NEZIHE: ensure Mongo _id matches @Id Long in Task.java
    taskId: 1001,
    courseId: 1,
    title: "Finish Assignment",
    type: "ASSIGNMENT",
    dueDate: new Date("2026-02-20T17:00:00Z"),
    isCompleted: false,
    isBonus: false,
    isPriority: true,
    priorityThresholdDays: 3,
    manualPriorityOverride: false,
    weight: 10,
    scorePercent: 0
  },
  {
    _id: NumberLong(1002), // NEZIHE
    taskId: 1002,
    courseId: 1,
    title: "Read Docs",
    type: "STUDY",
    dueDate: new Date("2026-02-18T17:00:00Z"),
    isCompleted: false,
    isBonus: false,
    isPriority: false,
    priorityThresholdDays: 5,
    manualPriorityOverride: false,
    weight: 5,
    scorePercent: 0
  }
]);


// ===============================
// REMINDER SERVICE COLLECTION
// ===============================
db = db.getSiblingDB("reminderdb");

// Use collection name "reminders" to match Reminder domain. #Nezihe
const reminderCol = db.getCollection("reminders");

// Prevent duplicate seeds on container restart
if (!reminderCol.findOne({ reminderId: 5001 })) {

    // Insert seed reminders aligned with ReminderRequestDTO.
    // Added reminderId and taskId to support task-reminder relationship. #Nezihe
    reminderCol.insertMany([
        {
            reminderId: 5001,
            taskId: 1001,
            message: "Lab 3 deadline approaching",
            scheduledAt: new Date("2026-02-18T14:00:00Z"),
            wasSent: false
        },
        {
            reminderId: 5002,
            taskId: 1002,
            message: "Sprint report checkpoint",
            scheduledAt: new Date("2026-02-19T15:00:00Z"),
            wasSent: false
        }
    ]);
}
