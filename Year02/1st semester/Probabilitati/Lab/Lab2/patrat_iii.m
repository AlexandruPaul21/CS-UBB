function patrat_iii(N=500)
 %N=numarul de puncte generate
 clf;hold on;axis square; axis off;
 rectangle('Position',[0 0 1 1],'FaceColor','w');
 count=0;
 for i=1:N
    x=rand;y=rand;
    AP=pdist([x y;0 0]);BP=pdist([x y;1 0]);
    CP=pdist([x y;1 1]);DP=pdist([x y;0 1]);
    triobtuze=0;
    if AP^2+BP^2<1
        triobtuze++;
    endif
    if BP^2+CP^2<1
        triobtuze++;
    endif
    if CP^2+DP^2<1
        triobtuze++;
    endif
    if DP^2+AP^2<1
        triobtuze++;
    endif
    if triobtuze==2
        plot(x,y,'hr','MarkerSize',7,'MarkerFaceColor','r');
        count++;
    endif
 endfor
 fprintf('Probabilitatea estimata, ceruta la iii), este %3.2f.\n',count/N);