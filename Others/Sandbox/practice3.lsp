(defun elim (L)
    (cond 
        ((and (numberp L) (= 0 (mod L 3))) nil)
        ((atom L) (list L))
        (T (list (mapcan #'elim L)))
    )
)

(defun func (L niv)
    (cond 
        ((and (numberp L) (= 0 (mod L 2)) (= 1 (mod niv 2))) nil)
        ((atom L) (list L))
        (T (list (mapcan #'(lambda (x) (func x (+ niv 1))) L)))
    )
)

(defun nr_nod (L K niv)
    (cond 
        ((and (atom L) (= K niv)) 1)
        ((atom L) 0)
        (T (apply #'+ (mapcar #'(lambda (x) (nr_nod x K (+ niv 1))) L)))
    )
)

(defun sau (L)
    (cond 
        ((null L) nil)
        (T (or (car L) (sau (cdr L))))
    )
)

(defun fnd (L E)
    (cond 
        ((and (atom L) (equal L E)) T)
        ((atom L) nil)
        (T (funcall #'sau (mapcar #'(lambda (x) (fnd x E)) L)))
    )
)