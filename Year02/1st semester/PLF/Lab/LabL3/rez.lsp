;subs (x - el, e - el, s - el)
; x - elementul de prelucat (poate fi lista, atom)
; e - elementul ce trebuie inlocuit
; s - elementul cu care va fi inlocuit 
(defun subs (x e s)
    (cond 
        ((and (atom x) (equal x e)) s)
        ((atom x) x)
        (t (mapcar #'(lambda (x) (subs x e s)) x))
    )
)