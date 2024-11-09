package com.example.quizapp;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Arrays;

public class FirestoreUtils {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void addSampleQuestions() {
        // Nature questions
        Question natureQuestion1 = new Question(
                "What is the largest mammal on Earth?",
                Arrays.asList("Elephant", "Blue Whale", "Shark", "Giraffe"),
                "Blue Whale",
                "Nature"
        );

        Question natureQuestion2 = new Question(
                "What tree is known for producing acorns?",
                Arrays.asList("Pine", "Oak", "Maple", "Birch"),
                "Oak",
                "Nature"
        );

        Question natureQuestion3 = new Question(
                "Which animal is known as the king of the jungle?",
                Arrays.asList("Lion", "Tiger", "Elephant", "Giraffe"),
                "Lion",
                "Nature"
        );

        Question natureQuestion4 = new Question(
                "What is the largest species of shark?",
                Arrays.asList("Great White", "Whale Shark", "Hammerhead", "Bull Shark"),
                "Whale Shark",
                "Nature"
        );

        Question natureQuestion5 = new Question(
                "What bird is known for its colorful feathers and large beak?",
                Arrays.asList("Peacock", "Eagle", "Toucan", "Parrot"),
                "Toucan",
                "Nature"
        );

        // Science questions
        Question scienceQuestion1 = new Question(
                "Which planet is known as the Red Planet?",
                Arrays.asList("Earth", "Mars", "Jupiter", "Saturn"),
                "Mars",
                "Science"
        );

        Question scienceQuestion2 = new Question(
                "What is the chemical symbol for water?",
                Arrays.asList("O2", "H2O", "CO2", "NaCl"),
                "H2O",
                "Science"
        );

        Question scienceQuestion3 = new Question(
                "What is the boiling point of water?",
                Arrays.asList("50°C", "75°C", "100°C", "150°C"),
                "100°C",
                "Science"
        );

        Question scienceQuestion4 = new Question(
                "Who is known as the father of modern physics?",
                Arrays.asList("Isaac Newton", "Albert Einstein", "Galileo Galilei", "Niels Bohr"),
                "Albert Einstein",
                "Science"
        );

        Question scienceQuestion5 = new Question(
                "What gas do plants absorb from the atmosphere for photosynthesis?",
                Arrays.asList("Oxygen", "Carbon Dioxide", "Nitrogen", "Hydrogen"),
                "Carbon Dioxide",
                "Science"
        );

        // Computer Science questions
        Question csQuestion1 = new Question(
                "What does CPU stand for?",
                Arrays.asList("Central Processing Unit", "Central Process Unit", "Computer Personal Unit", "Central Processor Unit"),
                "Central Processing Unit",
                "Computer Science"
        );

        Question csQuestion2 = new Question(
                "What is the binary representation of the number 5?",
                Arrays.asList("101", "100", "110", "111"),
                "101",
                "Computer Science"
        );

        Question csQuestion3 = new Question(
                "What does HTML stand for?",
                Arrays.asList("Hyper Text Markup Language", "High Text Markup Language", "Hyperlink Text Markup Language", "Home Tool Markup Language"),
                "Hyper Text Markup Language",
                "Computer Science"
        );

        Question csQuestion4 = new Question(
                "Which programming language is known for its 'write once, run anywhere' philosophy?",
                Arrays.asList("C", "Java", "Python", "Ruby"),
                "Java",
                "Computer Science"
        );

        Question csQuestion5 = new Question(
                "What is the function of the ALU in a computer?",
                Arrays.asList("Control Unit", "Arithmetic Logic Unit", "Memory Unit", "Input Unit"),
                "Arithmetic Logic Unit",
                "Computer Science"
        );

        // Add each question to Firestore
        addQuestionToFirestore(natureQuestion1);
        addQuestionToFirestore(natureQuestion2);
        addQuestionToFirestore(natureQuestion3);
        addQuestionToFirestore(natureQuestion4);
        addQuestionToFirestore(natureQuestion5);

        addQuestionToFirestore(scienceQuestion1);
        addQuestionToFirestore(scienceQuestion2);
        addQuestionToFirestore(scienceQuestion3);
        addQuestionToFirestore(scienceQuestion4);
        addQuestionToFirestore(scienceQuestion5);

        addQuestionToFirestore(csQuestion1);
        addQuestionToFirestore(csQuestion2);
        addQuestionToFirestore(csQuestion3);
        addQuestionToFirestore(csQuestion4);
        addQuestionToFirestore(csQuestion5);
    }

    // This function will now add the userId to each document
    private void addQuestionToFirestore(Question question) {
        db.collection("questions")
                .add(question)
                .addOnSuccessListener(documentReference -> {
                    System.out.println("Question added with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    System.err.println("Error adding question: " + e.getMessage());
                });
    }

    // Save the user's score to Firestore, including their userId
    public void saveUserScore(int score, int totalQuestions, double percentage) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // Get current user's UID
        long timestamp = System.currentTimeMillis(); // Get the current timestamp

        // Check if a score entry already exists for the current user
        db.collection("scores")
                .whereEqualTo("userId", userId) // Filter by userId
                .get() // Removed the timestamp filter
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        // If no document exists, add a new score entry
                        ScoreEntry scoreEntry = new ScoreEntry(score, totalQuestions, percentage, timestamp, userId);
                        db.collection("scores")
                                .document(userId + "_" + timestamp) // Use userId and timestamp as document ID
                                .set(scoreEntry)
                                .addOnSuccessListener(aVoid -> {
                                    System.out.println("Score added successfully");
                                })
                                .addOnFailureListener(e -> {
                                    System.err.println("Error adding score: " + e.getMessage());
                                });
                    } else {
                        // If a score entry already exists, you can update it instead of adding a new one
                        System.out.println("Score already exists for this user.");
                    }
                })
                .addOnFailureListener(e -> {
                    System.err.println("Error checking if score exists: " + e.getMessage());
                });
    }
}
