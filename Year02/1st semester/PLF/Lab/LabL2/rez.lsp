; trans (l : lista)
; l - reprezentarea a 2-a listei ce trebuie convertita in prima reprezentare
(defun trans(l)
    (cond 
        ((AND (null (cadr l)) (null (caddr l ))) (list (car l) 0))
        ((null (cadr l)) (append (list (car l) 1) (trans (caddr l))))
        ((null (caddr l)) (append (list (car l) 1) (trans (cadr l))))
        (T (append (append (list (car l) 2) (trans (cadr l))) (trans (caddr l))))
    )
)