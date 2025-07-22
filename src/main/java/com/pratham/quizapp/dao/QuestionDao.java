package com.pratham.quizapp.dao;

import com.pratham.quizapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {
    List<Question> findByCategory(String category);

    List<Question> findByDifficultyLevel(String difficultyLevel);

    @Query(value = "SELECT * FROM question q Where q.category=:category ORDER BY RAND() LIMIT :numQ ", nativeQuery = true)
    List<Question> findNRandomQuestionsByCategory(int numQ, String category);
}
