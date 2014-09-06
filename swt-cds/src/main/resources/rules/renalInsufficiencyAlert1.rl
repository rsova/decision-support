@prefix vs: <http://datasets.caregraf.org/vs/>.
@prefix chcss: <http://datasets.caregraf.org/chcss/>.
@prefix dc: <http://purl.org/dc/elements/1.1/>.
@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>.
@prefix owl: <http://www.w3.org/2002/07/owl#>.
@prefix xsd: <http://www.w3.org/2001/XMLSchema#>.
@prefix fms: <http://datasets.caregraf.org/fms/>.
@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>.

[RenalInsufficiencyAdvice: (?d chcss:RenalInsufficiencyAdvice http://schemes.caregraf.info/icd9cm#584_9) <- (?d chcss:test-63_07 http://eleu.qbase.com/60-173) (?d chcss:alert-63_07 'H')]
#[RenalInsufficiency: (?d rdf:type http://schemes.caregraf.info/icd9cm#584_9) <- (?d chcss:test-63_07 http://eleu.qbase.com/60-173) (?d chcss:alert-63_07 'H')]
#[RenalInsufficiencyAdvice: (?d chcss:advice 'Renal insufficiency Advice. Source XXX') <- (?d rdf:type http://schemes.caregraf.info/icd9cm#584_9)]
