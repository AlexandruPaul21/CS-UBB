(defun replace1(L e1 e2)
    (cond
        ((and (atom L) (equal L e1)) e2)
        ((atom L) L)
        (T (mapcar #'(lambda (x) (replace1 x e1 e2)) L))
    )
)

(defun repl (A K niv)
    (cond 
        ((null A) nil)
        ((and (atom A) (= niv K)) 0)
        ((atom A) A)
        (T (mapcar #'(lambda (x) (repl x K (+ niv 1))) A))
    )
)

(defun subs (L e)
    (cond 
        ((numberp L) e)
        ((atom L) L)
        (T (mapcar #'(lambda (x) (subs x e)) L))
    )
)