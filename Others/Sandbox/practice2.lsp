(defun elim (L niv)
    (cond 
        ((and (atom L) (not (numberp L)) (= 0 (mod niv 2))) nil)
        ((atom L) L)
        (T (mapcar #'(lambda (x) (elim x (+ niv 1))) L))
    )
)

(defun elim_main (L) (elim L 0))

(defun h(L nod g)
    (cond 
        ((atom L) -1)
        ((and (listp L) (equal g nil) (not (equal nod (car L))))
            (apply #'max (mapcar #'(lambda (x) (h x nod nil)) L))
        )
        (T (+ 1 (apply #'max (mapcar #'(lambda (x) (h x nod T)) L))))
    )
)

(defun func (L niv)
    (cond 
        ((and (atom L) (= 0 (mod niv 2))) (list L))
        ((atom L) ())
        (T (mapcan #'(lambda (x) (func x (+ niv 1))) L))
    )
)

(defun main (L) (func L '-1))

(defun repl (L niv)
    (cond 
        ((and (numberp L) (= 1 (mod L 2)) (= 0 (mod niv 2))) (+ L 1))
        ((atom L) L)
        (T (mapcar #'(lambda (x) (repl x (+ niv 1))) L))
    )
)