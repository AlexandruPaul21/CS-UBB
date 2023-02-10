%Subiect Practic R4.2
%Nume,Prenume,Grupa: Pop Mihai-Daniel, 225/2
% R4.2 Intr-o urna sunt 4 bile galbene, 5 bile negre, 2 bile albe si 3 bile albastre. 
% Se extrag aleator: a)cu returnare, b)fara returnare 4 bile din urna.
% Estimati prin simulari pentru cazurile a), respectiv b), probabilitatile evenimentelor:
% A:s-a extras cel mult o bila alba;
% B:nu s-a extras nicio bila galbena
% C:s-au extras cel putin 2 bile negre.

pkg load statistics

function Practic
  
  N = 1000
  
  g = 0; %bile galbene
  n = 1; %bile negre
  a = 2; %bile albe
  b = 3; %bile blue(albastre)

  %se extrag aleator a)cu returnare , b)fara returnare 4 bile

  bileG = zeros(1,4);
  bileN = ones(1,5);
  bileA = ones(1,2).*2;
  bileB = ones(1,3).*3;

  urna = [bileG,bileN,bileA,bileB]; %urna cu toate bilele
  
  probA = 0;
  probB = 0;
  probC = 0;
  
  probA2 = 0;
  probB2 = 0;
  probC2 = 0;

  disp 'Work in progress, please wait! '
  
  for i=1:N
    
    if (i*100<1000)
      disp 'Still working'
    endif
    
    %a)
    extragere2 = randsample(urna, 4, replacement = true);
    
    if ( sum(ismember(extragere2,2))<=1 )
      probA2++;
    endif
    
    if( sum(ismember(extragere2,0))==0 )
      probB2++;
    endif
    
    if( sum(ismember(extragere2,1))>=2 )
      probC2++;
    endif
    
    %b)
    extragere = randsample(urna, 4, replacement = false);

    if ( sum(ismember(extragere,2))<=1 )
      probA++;
    endif
    
    if( sum(ismember(extragere,0))==0 )
      probB++;
    endif
    
    if( sum(ismember(extragere,1))>=2 )
      probC++;
    endif
    
  endfor
   
   %a)
   disp ' '
   disp 'Subpunctul a) cu returnare'
   disp 'Probabilitatea cel mult o bila alba: '
   PA = probA2/N
   
   disp 'Probabilitatea nicio bila galbena: '
   PB = probB2/N
   
   disp 'Probabilitatea cel putin 2 bile negre: '
   PC = probC2/N
   
   %b)
   disp ' '
   disp 'Subpunctul b) fara returnare'
   disp 'Probabilitatea cel mult o bila alba: '
   PA = probA/N
   
   disp 'Probabilitatea nicio bila galbena: '
   PB = probB/N
   
   disp 'Probabilitatea cel putin 2 bile negre: '
   PC = probC/N
   
  
endfunction