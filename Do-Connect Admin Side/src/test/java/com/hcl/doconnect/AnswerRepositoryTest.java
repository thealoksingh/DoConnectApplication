package com.hcl.doconnect;

 
import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.mockito.Mockito.times;

import static org.mockito.Mockito.verify;

import static org.mockito.Mockito.when;
 
import java.util.ArrayList;

import java.util.List;
 
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import com.scoe.doconnect.model.Answer;
import com.scoe.doconnect.model.Question;
import com.scoe.doconnect.model.User;
import com.scoe.doconnect.repository.AnswerRepository;

 class AnswerRepositoryTest {

    @Mock

    private AnswerRepository answerRepository;

    private List<Answer> answerList;

    @SuppressWarnings("deprecation")
	@BeforeEach

     void setup() {

        MockitoAnnotations.initMocks(this);

        answerList = new ArrayList<>();

        answerList.add(new Answer(1L, "Answer 1", 1, "APPROVED", new ArrayList<>(), new User(), new Question()));

        answerList.add(new Answer(2L, "Answer 2", 2,"APPROVED", new ArrayList<>(), new User(), new Question()));

    }

    @Test

     void testFindByQuestionId() {

        Long questionId = 1L;

        when(answerRepository.findByQuestionId(questionId)).thenReturn(answerList);

        List<Answer> answers = answerRepository.findByQuestionId(questionId);

        verify(answerRepository, times(1)).findByQuestionId(questionId);

        assertFalse(answers.isEmpty());

    }

    @Test

     void testFindByStatus() {

        String status = "APPROVED";

        when(answerRepository.findByStatus(status)).thenReturn(answerList);

        List<Answer> answers = answerRepository.findByStatus(status);

        verify(answerRepository, times(1)).findByStatus(status);

        assertFalse(answers.isEmpty());

    }

}
