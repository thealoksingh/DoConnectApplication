package com.hcl.doconnect;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.junit.jupiter.api.Assertions.assertNull;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.never;

import static org.mockito.Mockito.times;

import static org.mockito.Mockito.verify;

import static org.mockito.Mockito.when;

import java.util.List;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;

import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import com.hcl.doconnect.exception.AnswerNotFoundException;

import com.hcl.doconnect.model.Answer;

import com.hcl.doconnect.model.Like;

import com.hcl.doconnect.model.Question;

import com.hcl.doconnect.repository.AnswerRepository;

import com.hcl.doconnect.repository.LikeRepository;

import com.hcl.doconnect.repository.QuestionRepository;
import com.hcl.doconnect.service.AnswerServiceImpl;


 class AnswerServiceTest {

	@Mock

	private AnswerRepository answerRepository;

	@Mock

	private QuestionRepository questionRepository;

	@Mock

	private LikeRepository likeRepository;

	@InjectMocks

	private AnswerServiceImpl answerService;

	@SuppressWarnings("deprecation")
	@BeforeEach

	 void setUp() {

		MockitoAnnotations.initMocks(this);

	}

	@Test

	 void testFindById() throws AnswerNotFoundException {

		Long id = 1L;

		Answer answer = new Answer();

		answer.setId(id);

		when(answerRepository.findById(id)).thenReturn(Optional.of(answer));

		Answer result = answerService.findById(id);

		assertEquals(answer, result);

	}

	@Test

	 void testFindById_AnswerNotFound() {

		Long id = 1L;

		when(answerRepository.findById(id)).thenReturn(Optional.empty());

		assertThrows(AnswerNotFoundException.class, () -> answerService.findById(id));

	}

	@Test

	 void testSave() {

		Answer answer = new Answer();

		Question question = new Question();

		question.setId(1L);

		answer.setQuestion(question);

		when(questionRepository.findById(1L)).thenReturn(Optional.of(question));

		Answer result = answerService.save(answer);

		assertEquals(answer, result);

		verify(answerRepository, times(1)).save(answer);

	}

	@Test

	 void testSave_QuestionNotFound() {

		Answer answer = new Answer();

		Question question = new Question();

		question.setId(1L);

		answer.setQuestion(question);

		when(questionRepository.findById(1L)).thenReturn(Optional.empty());

		assertNull(answerService.save(answer));

		verify(answerRepository, never()).save(answer);

	}

	@Test

	 void testDelete() {

		Answer answer = new Answer();

		answer.setId(1L);

		answerService.delete(answer);

		verify(answerRepository, times(1)).delete(answer);

	}

	@Test

	 void testApproveAnswer() {

		Long id = 1L;

		Answer answer = new Answer();

		answer.setId(id);

		answer.setStatus("PENDING");

		when(answerRepository.findById(id)).thenReturn(Optional.of(answer));

		answerService.approveAnswer(id);

		assertEquals("APPROVED", answer.getStatus());

		verify(answerRepository, times(1)).save(answer);

	}

	@Test

	 void testDeleteAnswer() {

		Long id = 1L;

		Answer answer = new Answer();

		answer.setId(id);

		when(answerRepository.findById(id)).thenReturn(Optional.of(answer));

		boolean result = answerService.deleteAnswer(id);
		assertTrue(result);

		verify(answerRepository, times(1)).delete(answer);

	}

	@Test

	 void testDeleteAnswer_AnswerNotFound() {

		Long id = 1L;

		when(answerRepository.findById(id)).thenReturn(Optional.empty());

		boolean result = answerService.deleteAnswer(id);

		assertFalse(result);

	}

	@Test

	 void testGetAllPendingAnswer() {

		when(answerRepository.findByStatus("PENDING")).thenReturn(List.of(new Answer()));

		List<Answer> result = answerService.getAllPendingAnswer();

		assertEquals(1, result.size());

	}

	@Test

	 void testGetAllApprovedAnswer() {

		when(answerRepository.findByStatus("APPROVED")).thenReturn(List.of(new Answer()));

		List<Answer> result = answerService.getAllApprovedAnswer();

		assertEquals(1, result.size());

	}

	@Test

	 void testViewAnswersWithQuestionId() {

		Long questionId = 1L;

		Question question = new Question();

		question.setId(questionId);

		Answer answer1 = new Answer();

		answer1.setQuestion(question);

		Answer answer2 = new Answer();

		answer2.setQuestion(question);

		when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));

		when(answerRepository.findByQuestionId(questionId)).thenReturn(List.of(answer1, answer2));

		List<Answer> result = answerService.viewAnswersWithQuestionId(questionId);

		assertEquals(2, result.size());

	}

	@Test

	 void testViewAnswersWithQuestionId_QuestionNotFound() {

		Long questionId = 1L;

		when(questionRepository.findById(questionId)).thenReturn(Optional.empty());

		assertNull(answerService.viewAnswersWithQuestionId(questionId));

	}

	@Test

	 void testCheckLikedByUser() {

		Long answerId = 1L;

		Long userId = 1L;

		Like like = new Like();

		like.setAnswerId(answerId);

		like.setUserId(userId);

		when(likeRepository.findByAnswerIdAndUserId(answerId, userId)).thenReturn(like);

		boolean result = answerService.checkLikedByUser(answerId, userId);

		assertTrue(result);

	}

	@Test

	 void testCheckLikedByUser_LikeNotFound() {

		Long answerId = 1L;

		Long userId = 1L;

		when(likeRepository.findByAnswerIdAndUserId(answerId, userId)).thenReturn(null);

		boolean result = answerService.checkLikedByUser(answerId, userId);

		assertFalse(result);

	}

}
