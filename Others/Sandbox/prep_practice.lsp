(defun rev (L i)
    (cond 
        ((null L) i)
        ((listp (car L)) (rev (cdr L) (cons (rev (car L) nil) i)))
        (T (rev (cdr L) (cons (car L) i)))
    )
)

;10)
;a)
(defun prod (L) 
    (cond
        ((null L) 1)
        ((numberp (car L)) (* (car L) (prod (cdr L))))
        (T (prod (cdr L)))
    )
)

;b)
(defun per (c L)
    (cond 
        ((null L) nil)
        (T (cons (list c (car L)) (per c (cdr L))))
    )
)

(defun main (L)
    (cond 
        ((null L) nil)
        (T (append (per (car L) (cdr L)) (main (cdr L))))
    )
)

;c)
(defun evaluate (L)
    (cond
        ((null L) 0)
        ((numberp (car L)) (car L))
        ((eq (car L) '+) (+ (evaluate (cdr L)) (evaluate (cddr L)))) 
        ((eq (car L) '-) (- (evaluate (cdr L)) (evaluate (cddr L))))
        ((eq (car L) '*) (* (evaluate (cdr L)) (evaluate (cddr L))))
        ((eq (car L) '/) (/ (evaluate (cdr L)) (evaluate (cddr L))))
    )
)

;sortare

(defun getSm (L E)
    (cond
        ((null L) nil)
        ((< (car L) E) (cons (car L) (getSm (cdr L) E)))
        (T (getSm (cdr L) E))
    )
)

(defun getBg (L E)
    (cond
        ((null L) nil)
        ((> (car L) E) (cons (car L) (getBg (cdr L) E)))
        (T (getBg (cdr L) E))
    )
)

(defun getEq (L E)
    (cond 
        ((null L) nil)
        ((= (car L) E) (cons E (getEq (cdr L) E)))
        (T (getEq (cdr L) E))
    )
)

(defun quicks (L)
    (cond 
        ((null L) nil)
        (T (append (quicks (getSm L (car L))) (getEq L (car L)) (quicks (getBg L (car L)))))
    )
)

(defun rm (L) 
    (cond 
        ((= (length L) 1) (list (car L)))
        ((= (car L) (cadr L)) (rm (cdr L)))
        (T (cons (car L) (rm (cdr L))))
    )
)

(defun get_elem (L last)
    (cond 
        ((null L) (list last))
        ((atom (car L)) (get_elem (cdr L) (car L)))
        (T (append (get_elem (car L) 0) (get_elem (cdr L) last) ))
    )
)

(defun is_munte(l f)
    (cond
        ((equal (length l) '1) 
        (cond
            ((equal f '1) T)
            (T nil)
        ))
        ((> (length l) '1)
        (cond
            ((and (= f '1 ) (> (car l) (cadr l))) (is_munte (cdr l) '1))
            ((and (= f '0 ) (< (car l) (cadr l))) (is_munte (cdr l) '0))
            ((and (= f '0 ) (> (car l) (cadr l))) (is_munte (cdr l) '1))
            ((and (= f '-1) (< (car l) (cadr l))) (is_munte (cdr l) '0))
            (T nil)
        ))
        (T nil)
    )
)

(defun cauta (L E)
    (cond 
        ((null L) nil)
        ((and (atom (car L)) (eq (car L) E) T))
        ((atom (car L)) (cauta (cdr L) E))
        (T (or (cauta (car L) E) (cauta (cdr L) E)))
    )
)

(defun path (L X)
    (cond
        ((null L) nil) 
        ((eq (car L) X) (list X))
        ((equal (length L) 2) (cons (car L) (path (cadr L) X)))
        ((cauta (cadr L) X) (cons (car L) (path (cadr L) X)))
        (T (cons (car L) (path (caddr L) X)))
    )
)

;big numbers

(defun sum (N1 N2 tr)
    (cond 
        ((and (null N1) (null N2 ) (not (= tr 0))) (list tr))
        ((and (null N1) (null N2 )) nil)
        ((null N1) (cons (+ (car N2) tr) (sum nil (cdr N2) '0)))
        ((null N2) (cons (+ (car N1) tr) (sum (cdr N1) nil '0)))
        (T (cons (mod (+ (car N1) (car N2) tr) 10) (sum (cdr N1) (cdr N2) (floor (/ (+ (car N1) (car N2) tr) 10)))))
    )
)

(defun mul (N C tr)
    (cond 
        ((and (null N) (= tr 0)) nil)
        ((null N) (list tr))
        (T (cons (mod (+ (* (car N) C) tr) 10) (mul (cdr N) C (floor (/ (+ (* (car N) C) tr) 10)))))
    )
)

(defun rev (L A) 
    (cond
        ((null L) A)
        (T (rev (cdr L) (cons (car L) A)))
    )
)

(defun tranLN (L)
    (cond
        ((null L) 0)
        (T (+ (* (tranLN (cdr L)) 10) (car L)))
    )
)

(defun mul_main (N C)
    (tranLN (mul (rev N nil) C 0))
)

(defun main (N1 N2)
    (tranLN (sum (rev N1 nil) (rev N2 nil) 0))
)