function prob1
  A = [10, 7, 8, 7; 7, 5, 6, 5; 8, 6, 10, 9; 7, 5, 9, 10];
  b = [32; 23; 33; 31];
  x = A\b

  b_perturbat = [32.1; 22.9; 33.1; 30.9];
  x_perturbat = A\b_perturbat

  disp("b este perturbat");
  eroare_in = norm(b-b_perturbat)/norm(b);
  disp(num2str(eroare_in));
  eroare_out = norm(x-x_perturbat)/norm(x);
  disp(num2str(eroare_out));
  raport = eroare_out/eroare_in;
  disp(num2str(raport));

  A_perturbat = [10, 7, 8.1, 7.2; 7.08, 5.04, 6, 5; 8, 5.98, 9.89, 9; 6.99, 4.99, 9, 9.98];
  x_perturbat = A_perturbat\b;

  disp("");
  disp("A este perturbat");
  eroare_in = norm(A-A_perturbat)/norm(A);
  disp(num2str(eroare_in));
  eroare_out = norm(x - x_perturbat)/norm(x);
  disp(num2str(eroare_out));
  raport = eroare_out/eroare_in;
  disp(num2str(raport));

  %!!!problema nu este bine conditionata
