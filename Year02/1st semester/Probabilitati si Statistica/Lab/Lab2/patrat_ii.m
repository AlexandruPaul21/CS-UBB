function patrat_ii(N=500)
 %N=numarul de puncte generate
 clf;hold on;axis square;axis off;
 rectangle('Position',[0 0 1 1],'FaceColor','w');
 count=0;
 for i=1:N
    x=rand;y=rand;
    if pdist([x y;0.5 0.5])<pdist([x y; 0 0])&&...
       pdist([x y;0.5 0.5])<pdist([x y; 0 1])&&...
       pdist([x y;0.5 0.5])<pdist([x y; 1 0])&&...
       pdist([x y;0.5 0.5])<pdist([x y; 1 1])
            plot(x,y,'pb','MarkerSize',7,'MarkerFaceColor','b');
            count++;
    endif
 endfor
 fprintf('Probabilitatea estimata, ceruta la ii), este %3.2f.\n',count/N);