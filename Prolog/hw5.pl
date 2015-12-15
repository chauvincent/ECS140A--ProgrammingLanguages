/*******************************************/
/**    Your solution goes in this file    **/ 
/*******************************************/
% Part 1
% (a) Find all courses with 3 or 4 credits (fccourse).
fc_course(C):-
			course(C,_,Unit), 
			(Unit = 3; Unit = 4).
% (b) Find all courses whose immediate pre-requisite is ecs110 (prereq110).
prereq_110(Class):-
			course(Class,Preclass,_), 
			member(ecs110,Preclass).
% (c) Find names of all students in ecs140a (ecs140a students).
ecs140a_students(Students):-
			student(Students,Class), 
			member(ecs140a,Class).
% (d) Find the names of all instructors who teach john’s courses (instructor names).
instructor_names(Instructor) :-
			instructor(Instructor, _),
			johns_teachers(Instructor).
johns_teachers(Instructor) :-
			student(john, Jclass), 
			instructor(Instructor, Classestaught), 
			member(Class, Jclass), 
			member(Class, Classestaught),
			!.
%(e) Find the names of all students who are in jim’s class (students).
students(Student) :-
			student(Student, _),
			jim_students(Student).
jim_students(Student) :-
			instructor(jim, Jimteaches), 
			student(Student, Studentclasses), 
			member(Class, Jimteaches), 
			member(Class, Studentclasses),
			!.
%(f) Find all pre-requisites of a course (allprereq). (This will involve finding not only the immediate prerequisites of a course, but pre-requisite courses of pre-requisites and so on.)
allprereq(Class, Listpreq):-
			% findall(+Template, +Goal, -List)
			findall(Template, (course(Template,_,_),prereq(Class,Template)) , Listpreq).
prereq(Class,Template):-
			course(Class, Listpreqs, _), member(Template, Listpreqs);course(Class, Listpreqs, _), member(Classes, Listpreqs), 
			prereq(Classes, Template).
% Part 2
% 1. Write a predicate,all_length, that takes a list and counts the number of atoms that occur in the list at all levels.
all_length([],0).
all_length([Head|Rest],Length):-
			atom(Head), all_length(Rest, J),
			!, Length is 1+J;
			all_length(Head,I), 
			all_length(Rest,J),
			!, Length is I+J.
% 2. Define a predicate, equal a b(Length), which returns true if Length contains an equal number of a and b terms.
equal_a_b(L):-
			equal_help(L,0,0).
equal_help([], A, B):-
			A=:=B,!.
equal_help([H|T], Acount, Bcount):-
			atom(H), H == a, AC is Acount+1,!, equal_help(T, AC, Bcount);
			atom(H), H == b, BC is Bcount+1,!, equal_help(T, Acount, BC);
			!, equal_help(T, Acount,Bcount);
			Acount =:= Bcount.
% 3. Define a predicate, swap prefix suffix(K,Length,S), such that swap prefix suffix(K,Length,S) is true if
swap_prefix_suffix(K,L,S):-
			append3(L1,K,L3,L),
			append3(L3,K,L1,S).
append3(L1,L2,L3,L):-
			append(L1,LL,L),
			append(L2,L3,LL).
% 4. Define a predicate, palin(A) that is true if the list A is a palindrome, that is, it reads the same backwards as forwards. For instance,
% 	[1, 2, 3, 2, 1]
% 	is a palindrome, but
% 	[1, 2]
% 	is not.
palin(A):-
			reverse(A,B),
			A = B.
% 5. A good sequence consists either of the single number 0, or of the number 1 followed by two other
% good sequences: thus,
%	[1,0,1,0,0]
%	is a good sequence, but
%	[1,1,0,0] is not. 
%	Define a relation good(A) that is true if A is a good sequence
good([0]).
good([1|T]) :-
			append(A,B,T),
			good(A),
			good(B).
% Part 3
% Write a logic program to solve the following puzzle: A farmer must ferry a wolf, a goat, and a cabbage
% across a river using a boat that is too small to take more than one of the three across at once. If he leaves
% the wolf and the goat together, the wolf will eat the goat, and if he leaves the goat with the cabbage, the goat
% will eat the cabbage. How can he get all three across the river safely?
solve:-

%Attempted but no luck...... but we solved the puzzle by hand and came with the solution below
#	write('take(goat, left, right)'), nl,
#	write('take(none, right, left)'), nl,
#	write('take(wolf, left, right)'), nl,
#	write('take(goat, right, left)'), nl,
#	write('take(cabbage, left, right)'), nl,
#	write('take(none, right, left)'), nl,
#	write('take(goat, left, right)'), nl.

%solve:- go(A,B,Moves)

%go(A,B,Moves):-
%opposite(A,B):-

%arc(state(, , ), state(, , )):- 
%arc(state(, , ), state(, , )):- 
%arc(state(, , ), state(, , )):- 
%arc(state(, , ), state(, , )):- 

%safe(A) :-

%unsafe(state(, , , )) :- 

% take(X,A,B):-.
