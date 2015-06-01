package edu.umn.ecology.populus.model.bp;
import edu.umn.ecology.populus.math.*;
/*
H = Number of Hosts, P = Number of parasites, W = Number of free-living infective stages
a = birth rate, b = death rate, beta = transmission parameter; alpha = parasite induced death rate
mu = natural mortality rate of adult parasites, lambda = rate of production of infective stages by adult parasite
c =  death rate of infective stage;

*/
public class BPDeriv extends Derivative {
   public static final int kX = 0;//N+
   public static final int kY = 1;//N*
   public static final int kZ = 2;//N
   public static final int kR = 3;//R
   final private int modeltype, vartype;
   final private double kTau, kGamma;
   final private double alpha, rho, c, P, Q_, e;
   double gamma, tau;

   public void doDerivative( double t, double[] N, double[] dN ) {
      double psi, psistar, estar, temp, oma = 1.0 - alpha;
      double n1 = N[kX];
      double n2 = N[kY];
      double n3 = N[kZ];
      double r = N[kR];

      if (r != 0) {
         psi = P / (Q_ / r + 1 );
         psistar = psi * oma; // psi* = psi+ = psi ( 1- alpha)
      } else {
         psi = 0;
         psistar = 0;
      }

      if ( oma != 0) estar = e / oma; //e* = e+ = e /(1-alpha)
      else           estar = 0;

      if (vartype == BPParamInfo.variable) {
         if (P != 0) temp = psi / P;
         else        temp = 0;
         gamma = gamma * temp;
         tau = tau * temp;
      }

      /*note: these equations aren't exactly the same as in Populus 4.0b.
      so DON'T WORRY if the results don't match!! :-)*/
      switch( modeltype ) {
         case BPParamInfo.equable:
            dN[kX] = n1 * ( psistar - rho - tau) + n2 * psistar;//n+:x
            dN[kY] = ( n1 + n2) * gamma * n3  - n2 * ( rho + tau);//n*
            dN[kZ] = (psi - rho) * n3 - ( n1 + n2)*(gamma * n3 -  tau);// n
            dN[kR] = rho * ( c - r ) - estar * psistar * ( n1 + n2 ) - e * psi * n3;//r
            break;

         case BPParamInfo.seasonal:
            dN[kX] = n1 * ( psistar - tau)+  n2 * psistar;//n+
            dN[kY] = ( n1 + n2) * gamma * n3  - n2 * tau;//n*
            dN[kZ] = psi * n3 - ( n1 + n2)*(gamma * n3 -  tau);// n
            dN[kR] =  - estar * psistar * ( n1 + n2 ) - e * psi * n3;//r
            break;
      }
//      1{n+} : BPdf := n[1]*(psistar-rho-tau){+n[2]*psistar};
//      2{n*} : BPdf := (n[1]+n[2])*gamma*n[3]-n[2]*(rho+tau-psistar);
//      3{n } : BPdf := n[3]*(psi-rho)-(n[1]+n[2])*(gamma*n[3]-tau);
//      4{r } : BPdf := rho*(c-r)-estar*psistar*(n[1]+n[2])-e*psi*n[3];
      return ;
   }

   public void reset(){
      tau = kTau;
      gamma = kGamma;
   }

   public BPDeriv( int modelType, int varType, double time, double alpha, double gamma, double tau,
                   double rho, double c, double r0, double p, double q, double e, double d, double tI) {
      this.modeltype = modelType;
      this.vartype = varType;
      this.alpha = alpha;
      this.kGamma = this.gamma = gamma;
      this.kTau = this.tau = tau;
      this.rho = rho;
      this.c = c;
      this.e = e;
      this.P = p;
      this.Q_ = q;
      this.numVariables = 4;
   }
}
/*function BPdf(j:integer;t:extended;var N:RKNarray;var Pm:ParamArray):Extended; far;
var
  i         : integer;
  temp,
  c,gamma,r,alpha,oma,
  tau,rho,P,Q_        : extended;
  psi,psistar,e,estar : extended;
begin
  alpha := Pm[alphap];
  oma   := 1-alpha;
  gamma := Pm[gammap];
  tau   := Pm[taup];
  rho   := Pm[rhop];
  c     := Pm[cp];
  r     := N[4];
  P     := Pm[Pp];
  Q_    := Pm[Qp];
  e     := Pm[ep];

  if Q^.TheModel=Seasonal then rho := 0;

  if r<>0 then
  begin
    psi     := P/(Q_/r+1);
    psistar := psi*(oma);   {psi* = psi+ = psi(1-alpha) }
  end
  else
  begin
    psi     := 0;
    psistar := 0;
  end;

  if oma <> 0 then
    estar   := e/(oma)   {e* = e+ = e/(1-alpha) }
  else
    estar   := 0;

  if Q^.Prop then
  begin
    if P <> 0 then temp := psi/P
              else temp := 0;
    gamma := gamma*temp;
    tau   := tau  *temp;
  end;

  case j of
    1{n+} : BPdf := n[1]*(psistar-rho-tau){+n[2]*psistar};
    2{n*} : BPdf := (n[1]+n[2])*gamma*n[3]-n[2]*(rho+tau-psistar);
    3{n } : BPdf := n[3]*(psi-rho)-(n[1]+n[2])*(gamma*n[3]-tau);
    4{r } : BPdf := rho*(c-r)-estar*psistar*(n[1]+n[2])-e*psi*n[3];
  end

{  for i:=1 to 3 do
  begin
    e[i]   := Pm[ep[i]];
    P[i]   := Pm[Pp[i]];
    Q_[i]  := Pm[Qp[i]];
    if r<>0 then
      psi[i] := P[i]/(Q_[i]/r+1)
    else
      psi[i] := 0;
  end;

  if Q^.Prop then
  begin
    if P[3] <> 0 then temp  := psi[3]/P[3]
                 else temp := 0;
    gamma := gamma*temp;
    tau   := tau  *temp;
  end;
}
end; {df}


procedure Postdf(t:extended;var n:RKNarray;var Pm:ParamArray;var RK); far;
var
  F1,F2,Ntotal:extended;
  RKR:RungeKuttaRec absolute RK;
begin
  Ntotal := n[1]+n[2]+n[3];
  if Ntotal<>0 then
  begin
    F1:=n[1]/Ntotal; {n+}
    F2:=n[2]/Ntotal; {n*}
  end
  else
  begin
    F1:=0;
    F2:=0;
  end;
  Q^.Data^.Add(F1d,t,F1);
  Q^.Data^.Add(F2d,t,F2);
  Q^.Data^.Add(NTd,t,NTotal);
  {store max Ntotal for plot scaling}
  Q^.NMax := max(q^.NMax,NTotal);

  if (Q^.TheModel=Seasonal) and (t-Q^.tlast>=Pm[dtp]) then {do transfer}
  begin
    Q^.tlast := Q^.tlast+Pm[dtp];
    N[4] := Pm[Np_[4]];
    n[1] := n[1]*Pm[dp];
    n[2] := n[2]*Pm[dp];
    n[3] := n[3]*Pm[dp];
  end;
end;

*/

