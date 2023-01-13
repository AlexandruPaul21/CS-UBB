m=1000;
disp('i)')
sume_posibile=4:24;
zaruri=randi(6,4,m);
sume_sim=sum(zaruri);
frecv_abs=hist(sume_sim,sume_posibile);
sume_frecv_abs=[sume_posibile;frecv_abs]'
disp('ii)')
clf;hold on;grid on;
xticks(sume_posibile);xlim([3,25]); %optional
yticks(0:0.01:0.14);ylim([0 0.14]); %optional
bar(sume_posibile,frecv_abs/m,'hist','FaceColor','b'); 
frecv_abs_max=max(frecv_abs)
sume_cele_mai_frecvente...
=sume_posibile(frecv_abs==frecv_abs_max)
disp('iii)')
sume_teor=[];
 for i1=1:6
   for i2=1:6
     for i3=1:6
       for i4=1:6
         sume_teor=[sume_teor i1+i2+i3+i4];
       endfor
     endfor
   endfor
 endfor
frecv_abs=hist(sume_teor,sume_posibile);
sume_frecv_abs=[sume_posibile;frecv_abs]'
bar(sume_posibile,frecv_abs/length(sume_teor),...
'FaceColor','y'); 
frecv_abs_max=max(frecv_abs)
sume_cele_mai_frecvente...
=sume_posibile(frecv_abs==frecv_abs_max)
legend('frecvente relative',...
'probabilitati');
set(findobj('type','patch'),'facealpha',0.7);
disp('iv)')
prob_cond_sim=sum((sume_sim>=10)&(sume_sim<=20))/...
sum(sume_sim<=20)
prob_cond_teor=sum((sume_teor>=10)&(sume_teor<=20))/...
sum(sume_teor<=20)
