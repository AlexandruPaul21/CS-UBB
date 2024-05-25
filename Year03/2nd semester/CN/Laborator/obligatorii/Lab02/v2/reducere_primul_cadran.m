function val = reducere_primul_cadran(x)
  x = mod(x, 2 * pi);

  if x >= 0 && x < pi / 2
    % cadran 1
    semn_sin = 1;
    semn_cos = 1;
    val = x;
  elseif x >= pi / 2 && x < pi
    % cadran 2
    semn_sin = 1;
    semn_cos = -1;
    val = pi - x;
  elseif x >= pi && x < 3*pi/2
    % cadran 3
    semn_sin = -1;
    semn_cos = -1;
    val = x - pi;
  else
    % cadran patru
    semn_sin = -1;
    semn_cos = 1;
    val = 2*pi - x;
  endif
endfunction

