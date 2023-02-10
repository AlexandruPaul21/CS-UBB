function mers_axa_b_c(p=0.7,k=5,m=2000)
positions_end=[];
steps2right=[];
for i=1:m
   [pos,steps]=mers_axa_a(p,k);
   positions_end=[positions_end;pos(end)];
   steps2right=[steps2right,steps];
end

N=histc(positions_end,-k:k);
bar(-k:k,N/m,'hist','FaceColor','b');
set(gca,'XTick',-k:k); grid on;
xlim([-k-1 k+1]);

fprintf('Poz. finale cele mai frecvente: %d.\n',find(N==max(N))-k-1);

fprintf('Media nr. de pasi la dr.: %3.2f.\n',mean(steps2right));