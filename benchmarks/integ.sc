(define (integrate-1D L U F)
 (let ((D (/ (- U L) 8.0)))
  (* (+ (* (F L) 0.5)
	(F (+ L D))
	(F (+ L (* 2.0 D)))
	(F (+ L (* 3.0 D)))
	(F (+ L (* 4.0 D)))
	(F (- U (* 3.0 D)))
	(F (- U (* 2.0 D)))
	(F (- U D))
	(* (F U) 0.5))
     D)))

(define (integrate-2D L1 U1 L2 U2 F)
 (integrate-1D L2 U2 (lambda (y) (integrate-1D L1 U1 (lambda (x) (F x y))) )))

(define (zark U V)
 (integrate-2D 0.0 U 0.0 V (lambda (X Y) (* X Y)) ))

(define (r-total N)
 (do ((I 1 (+ I 1))
      (Sum 0.0 (+ Sum (zark (* I 1.0) (* I 2.0)))))
   ((> I N) Sum)))

(define (i-total N)
 (do ((I 1 (+ I 1))
      (Sum 0.0 (+ Sum (let ((I2 (* (* I I) 1.0))) (* I2 I2)))))
   ((> I N) Sum)))

(define (error-sum-of-squares N)
 (do ((I 1 (+ I 1))
      (Sum 0.0 (+ Sum (let ((E (- (r-total I) (i-total I)))) (* E E)))))
   ((> I N) Sum)))

(begin (display (error-sum-of-squares 1000)) (newline))
