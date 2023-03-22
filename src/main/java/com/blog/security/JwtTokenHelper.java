package com.blog.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenHelper {
	
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 *60;
	
	private String secrete = "jwtTokenKey";
	
	//retrive username from jwt token
	public String getUserameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	//retrive expiration date from jwt
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	public <T> T getClaimFromToken(String token,java.util.function.Function<Claims,T> claimResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimResolver.apply(claims);
	}
	
	//retriving any info from token we will need a security key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secrete).parseClaimsJws(token).getBody();
	}
	
	//check if token has expired
	private Boolean isTokenExpired(String token) {
		final Date expirationDate = getExpirationDateFromToken(token);
		return expirationDate.before(new Date());
	}
	
	//genrate token for user
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<String, Object>();
		return doGenerateToken(claims, userDetails.getUsername());
	}
	
	//while creating the token
	//1. define claims of token,like issuer,Expiration,Subject and ID
	//2. sign jwt using HS512 algo and secrete key
	//3. According to jws Compact Seriazation
	
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000))
				.signWith(SignatureAlgorithm.HS256, secrete)
				.compact();
	}
	
	//validate token
	public Boolean validateToken(String token,UserDetails userDetails) {
		final String username=getUserameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	

}
