;a)
;gcd1 (a - intreg, b - intreg)
;a, b - numerele a caror gcd dorim sa il aflam
(defun gcd1(a b)
    (cond 
        ((equal b '0) a)
        (T (gcd1 b (mod a b)))
    )
)

;cmmc (a - intreg, b - intreg)
;a, b - numerele a caror cmmc dorim sa il aflam
(defun cmmc(a b)
    (/ (* a b) (gcd1 a b))
)

;main (l - lista)
;l - lista pentru care vom afla cmmc al elementelor
(defun main(l)
    (cond
        ((null l) 1)
        ((numberp (car l)) (cmmc (car l) (main (cdr l))))
        ((listp (car l)) (cmmc (main (car l)) (main (cdr l))))
        (T (main (cdr l)))
    )
)

;b)
;is_munte_main(l - lista)
;l - lista pe care o verificam daca e munte
(defun is_munte_main(l)
    (is_munte l '-1)
)

;is_munte(l - lista, f - intreg)
;l - lista pe care o verificam 
;f - un flag ce poate avea valoarea -1, 0, 1
(defun is_munte(l f)
    (cond
        ((equal (length l) '1) 
        (cond
            ((equal f '1) T)
            (T nil)
        ))
        ((> (length l) '1)
        (cond
            ((= f '1 ) 
                (cond 
                    ((> (car l) (cadr l)) (is_munte (cdr l) '1))
                    (T nil)
                )
            )
            ((= f '0 ) 
                (cond
                    ((< (car l) (cadr l)) (is_munte (cdr l) '0))
                    ((> (car l) (cadr l)) (is_munte (cdr l) '1))
                    (T nil)
                )
            )
            ((= f '-1)
                (cond
                    ((< (car l) (cadr l)) (is_munte (cdr l) '0))
                    (T nil)
                )
            )
            (T nil)
        ))
        (T nil)
    )
)

;c)
;max1(l - lista)
;l - lista din care dorim sa aflam maximul
(defun max1(l)
    (cond
        ((null l) -99999)
        (
            (numberp (car l)) (cond
                    ((> (car l) (max1(cdr l))) (car l))
                    (T (max1(cdr l))))
        )
        ((atom (car l)) (max1(cdr l)))
        ((> (max1(car l)) (max1(cdr l))) (max1(car l)))
        (T (max1(cdr l)))
    )
)

;replace1(l - lista, E - element)
;l - lista din care stergem 
;E - elemntul pe care il stergem
(defun replace1(l E)
    (cond
        ((null l) nil)
        ((numberp (car l)) (cond
            ((equal (car l) E) (replace1 (cdr l) E))
            (T (append (list (car l)) (replace1 (cdr l) E)))
            
        ))
        ((atom (car l)) (append (list (car l)) (replace1 (cdr l) E)))
        (T (append (list (replace1 (car l) E)) (replace1 (cdr l) E)))
    )
)

;replace_main(l - lista)
;l - lista din care dorim sa stergem valoarea maxima
(defun replace_main(l)
    (replace1 (list l) (max1 l))
)

;d)
;Prod(l - lista)
;l - lista in care dorim sa aflam produsul numerelelor pare
(defun Prod(l)
    (cond 
        ((null l) 1)
        ((numberp (car l)) 
        (cond 
            ((equal (mod (car l) '2) '0) (* (car l) (Prod (cdr l))))
            (T (Prod (cdr l)))
        ))
        ((listp (car l)) (* (Prod (car l)) (Prod (cdr l))))
        (T (Prod (cdr l)))
    )
)