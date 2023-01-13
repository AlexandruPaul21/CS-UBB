function patrat_i(N=500)
 %N=numarul de puncte generate
 clf;hold on;axis square; axis off;
 rectangle('Position',[0 0 1 1]);
 count=0;
 for i=1:N
    x=rand;y=rand;
    if pdist([x y;0.5 0.5])<0.5
        plot(x,y,'dm','MarkerSize',7,'MarkerFaceColor','m');
        count++;
    endif
 endfor
 fprintf('Probabilitatea estimata, ceruta la i), este %3.2f.\n',count/N);