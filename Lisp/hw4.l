;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;; PART 1 ;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;
(defun all-length (l)
	(cond ;if statement
		((null l) 0) ; if is empty, return 0
		((atom l) 1) ; if atom, return 1
		(T (+ (all-length (first l)) ; is a list, recurse
			(all-length (rest l))
			)
		)
	)
)
(defun range (l)
	(list (apply 'min l) ; get min from list 
		(apply 'max l) ; get max from list
	)
)
(defun before(a b l)
	(cond
		((null l)										nil) ; if empty
		((and (null a) (equal (first l) b)) 			(list (first l))) ; a is null, and list is at tail, then return tail
		((null a) 										(cons (first l) (before nil b (rest l)))) 
		((equal (first l) a) 							(cons (first l)(before nil b(rest l)))) 
		(T 												(before a b (rest l))) ; final condition: recurse
	)	
)
(defun split-if (func l)
	(list(remove-if func l) (remove-if-not func l))
)
(defun group(l n &optional list2)
	(cond 
		((and(null l)(null list2)) 						nil) ; if lists are null
		((= (list-length list2) n)						(cons list2 (group l n))) ; if list is size n recurse, make list of each
		((and (null l)(> (list-length list2) 0))		(cons list2 (group l n))) ; if list greater than 0 and l is null; recurse
		(T 												(group (rest l) n (append list2 (list (first l))))) ; return recurse, concatenated list
	)
)
(defun mostn (function l)
	(mostn2 function l ()  0) 
)
(defun mostn2 (function l list2 max)
	(cond	((null l) 									(list list2 max)) ; if null
		((> (funcall function (first l)) max) 			(mostn2 function (rest l) (list (first l)) (funcall function (first l)) )) ; if bigger val
		((equal (funcall function (first l)) max) 		(mostn2 function (rest l) (union list2 (list (first l))) max)) ; if equal
		((< (funcall function (first l)) max) 			(mostn2 function (rest l) list2 max)) ; if less than
	)
)
;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;; PART 2 ;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;
(defun match(l l2)
	(cond
		((or (null l)(null l2)) 						(equal l l2)) ; return true both list is nil
		((equal (first l) (quote ?) )					(match (rest l) (rest l2))) ; if is a '?', recurse the list
		((and (equal (first l) (quote !))(match (rest l) (rest l2)))) ; head is quote and recurse return true if both true  
		((equal (first l) (quote !) ) 					(match l (rest l2))) ;  if is a '!' and recurse
		(T												(and(equal (first l) (first l2)) (match (rest l) (rest l2)))) ; both equal then T
	)
)

;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;; PART 3 ;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;

; PSUEDOCODE FROM SLIDE:
; The logic is similar to match function
; elsif (l1’s head is ?): 
;     return match_var(l1’s rest, l2’s rest)
; elseif (l1’s head is ! and ??? match_var(l1’s rest, l2’s rest): return true
; elseif (l1’s head is !):  
; return ??? match_var(l1, l2’s rest)
; ------
; not done yet..
; elsif (l1’s head is ?): 
;     return match(l1’s rest, l2’s rest)
; elseif (l1’s head is ! and ??? match (l1’s rest, l2’s rest): return true
; elseif (l1’s head is !):  
; return ??? match(l1, l2’s rest)
(defun match-var(l1 l2)
	(reset l1) (match-help l1 l2)
)
(defun match-help(l1 l2)
	(cond
		((or (null l1) (null l2)) 			(equal l2 l1)) ; check for nils
		((null (first l1)) 					(and (equal (first l2)(first l1)) (match-help (rest l1) (rest l2))))
		((listp (first l1)) ; if is a list 
		(cond   
			((equal (first (first l1)) (quote ?)) ; elsif (l1’s head is ?):
			(eval (list 'setq (cadar l1) (list 'quote (first l2))))  ; bind symbols
			(match-help (rest l1) (rest l2))) ; return match_var(l1's rest, l2's rest)
			;elseif (l1’s head is ! and ??? match_var(l1’s rest, l2’s rest): return true
			((and (equal (first (first l1) ) (quote !)) (match-help (rest l1) (rest l2)))	T) 
			;elseif (l1’s head is !):  return ??? match(l1, l2’s rest)
			((and (equal (first (first l1) ) (quote !)))			(match-help l1 (rest l2))))
		) 
		(T (and (equal (first l1) (first l2)) (match-help (rest l1) (rest l2)))) ; if equal recurse the rest
	) 
)
; ??? reset all variables set by green match
(defun reset(l)
	(cond
		((null l) 0) ; if null, return nil 
		((listp (first l)) ; if is a list
		; is list l has list l_h and its head is !, set the symbol to nil, then recurse
		(cond   
			((equal (first (first l)) (quote !))	(eval (list 'setq (cadar l) nil)) (reset (rest l))) 
			(T 										(reset (rest l)))))
	(T (reset (rest l))))
)