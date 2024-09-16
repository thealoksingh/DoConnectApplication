package com.scoe.doconnect.filter;
import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.FilterChain;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.User;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import com.scoe.doconnect.util.JwtUtil;

@Component // running in the background
public class JwtFilter extends OncePerRequestFilter

{

	@Autowired

	JwtUtil jwtUtil;

	@Override

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)

			throws ServletException, IOException

	{

		String authorizationHeader = request.getHeader("Authorization");

		String token = null;

		String username = null;

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer "))

		{

			token = authorizationHeader.substring(7); // Bearer ahsgadjgf.hfkhgdxj.hfkhgsjkrghsxtgdthd

			System.out.println(token);

			username = jwtUtil.extractUsername(token);

		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)

		{

			UserDetails userDetails = User.withUsername(username).password("singh123").roles("owner").build();

			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					userDetails, null, new ArrayList<>());

			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(authenticationToken);

		}

		filterChain.doFilter(request, response);

	}

}
